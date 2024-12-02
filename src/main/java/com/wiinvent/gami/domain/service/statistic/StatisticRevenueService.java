package com.wiinvent.gami.domain.service.statistic;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.statistic.StatisticCheckpoint;
import com.wiinvent.gami.domain.entities.statistic.StatisticRevenue;
import com.wiinvent.gami.domain.entities.statistic.StatisticUser;
import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.statistic.RevenueExcelResponse;
import com.wiinvent.gami.domain.response.statistic.StatisticRevenueResponse;
import com.wiinvent.gami.domain.response.statistic.UserExcelResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.utils.ExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class StatisticRevenueService extends BaseService {

  public StatisticRevenueResponse getStatisticRevenue(String startDate, String endDate) {
    StatisticRevenueResponse response = new StatisticRevenueResponse();
    LocalDate start = DateUtils.convertStringToLocalDate(startDate);
    LocalDate end = DateUtils.convertStringToLocalDate(endDate);

    for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
      StatisticRevenue statisticRevenue = statisticRevenueStorage.findByDate(current);
      StatisticRevenueResponse.StatisticDaily statisticDaily = new StatisticRevenueResponse.StatisticDaily(current.toString(), statisticRevenue);
      response.addStatistic(statisticDaily);
    }
    return response;
  }

  public void updateStatisticRevenueToday() {
    LocalDate dateNow = DateUtils.nowDate();
    Long millisStartToday = DateUtils.getStartOfDay(dateNow);
    Long millisEndToday = DateUtils.getStartOfDay(dateNow.plusDays(1)) - 1;
    StatisticRevenue statisticRevenue = statisticRevenueStorage.findByDate(dateNow);
    if (statisticRevenue == null) {
      statisticRevenue = new StatisticRevenue();
      statisticRevenue.setDate(dateNow);
      statisticRevenue.setCreatedAt(DateUtils.getNowMillisAtUtc());
    }

    List<Package> dayPackages = packageStorage.findPackagesByDaySub(1);
    List<Package> weekPackages = packageStorage.findPackagesByDaySub(7);
    List<Package> monthPackages = packageStorage.findPackagesByDaySub(30);
    List<Package> chargePackages = packageStorage.findPackagesByType(ProductPackageType.CHARGE);
    Long buyPackageRevenue = packageHistoryStorage.countRevenueByPackageCode
        (millisStartToday, millisEndToday, chargePackages.stream().map(Package::getCode).toList());
    Long registerSubDayRevenue = packageHistoryStorage.countRevenueByPackageCode
        (millisStartToday, millisEndToday, dayPackages.stream().map(Package::getCode).toList());
    Long registerSubWeekRevenue = packageHistoryStorage.countRevenueByPackageCode
        (millisStartToday, millisEndToday, weekPackages.stream().map(Package::getCode).toList());
    Long registerSubMonthRevenue = packageHistoryStorage.countRevenueByPackageCode
        (millisStartToday, millisEndToday, monthPackages.stream().map(Package::getCode).toList());
    Long feeSubDayRevenue = 0L;
    Long feeSubWeekRevenue = 0L;
    Long feeSubMonthRevenue = 0L;
    Long adsRevenue = 0L;
    Long totalRevenue = 0L;
    statisticRevenue.setRegisterSubDayRevenue(registerSubDayRevenue == null ? 0 : registerSubDayRevenue);
    statisticRevenue.setRegisterSubWeekRevenue(registerSubWeekRevenue == null ? 0 : registerSubWeekRevenue);
    statisticRevenue.setRegisterSubMonthRevenue(registerSubMonthRevenue == null ? 0 : registerSubMonthRevenue);
    statisticRevenue.setBuyPackageRevenue(buyPackageRevenue == null ? 0 : buyPackageRevenue);
    statisticRevenue.setFeeSubDayRevenue(feeSubDayRevenue == null ? 0 : feeSubDayRevenue);
    statisticRevenue.setFeeSubWeekRevenue(feeSubWeekRevenue == null ? 0 : feeSubWeekRevenue);
    statisticRevenue.setFeeSubMonthRevenue(feeSubMonthRevenue == null ? 0 : feeSubMonthRevenue);
    statisticRevenue.setAdsRevenue(adsRevenue == null ? 0 : adsRevenue);
    statisticRevenue.setTotalRevenue(totalRevenue == null ? 0 : totalRevenue);

    statisticRevenueStorage.save(statisticRevenue);
  }

  public void updateStatisticRevenuePreDay() {
    LocalDate dateNow = DateUtils.nowDate();
    StatisticCheckpoint statisticCheckpoint = statisticCheckpointStorage.findById(Constants.STATISTIC_REVENUE_CHECK_POINT_ID);
    if (statisticCheckpoint == null) {
      statisticCheckpoint = new StatisticCheckpoint();
      statisticCheckpoint.setId(Constants.STATISTIC_REVENUE_CHECK_POINT_ID);
      statisticCheckpoint.setCheckPoint(dateNow.minusDays(30));
    }

    for (LocalDate current = statisticCheckpoint.getCheckPoint(); current.isBefore(dateNow); current = current.plusDays(1)) {
      Long millisStartToday = DateUtils.getStartOfDay(current);
      Long millisEndToday = DateUtils.getStartOfDay(current.plusDays(1)) - 1;
      StatisticRevenue statisticRevenue = statisticRevenueStorage.findByDate(current);
      if (statisticRevenue == null) {
        statisticRevenue = new StatisticRevenue();
        statisticRevenue.setDate(current);
      }

      List<Package> dayPackages = packageStorage.findPackagesByDaySub(1);
      List<Package> weekPackages = packageStorage.findPackagesByDaySub(7);
      List<Package> monthPackages = packageStorage.findPackagesByDaySub(30);
      List<Package> chargePackages = packageStorage.findPackagesByType(ProductPackageType.CHARGE);
      Long buyPackageRevenue = packageHistoryStorage.countRevenueByPackageCode
          (millisStartToday, millisEndToday, chargePackages.stream().map(Package::getCode).toList());
      Long registerSubDayRevenue = packageHistoryStorage.countRevenueByPackageCode
          (millisStartToday, millisEndToday, dayPackages.stream().map(Package::getCode).toList());
      Long registerSubWeekRevenue = packageHistoryStorage.countRevenueByPackageCode
          (millisStartToday, millisEndToday, weekPackages.stream().map(Package::getCode).toList());
      Long registerSubMonthRevenue = packageHistoryStorage.countRevenueByPackageCode
          (millisStartToday, millisEndToday, monthPackages.stream().map(Package::getCode).toList());
      Long feeSubDayRevenue = 0L;
      Long feeSubWeekRevenue = 0L;
      Long feeSubMonthRevenue = 0L;
      Long totalRevenue = 0L;
      Long adsRevenue = 0L;
      statisticRevenue.setRegisterSubDayRevenue(registerSubDayRevenue == null ? 0 : registerSubDayRevenue);
      statisticRevenue.setRegisterSubWeekRevenue(registerSubWeekRevenue == null ? 0 : registerSubWeekRevenue);
      statisticRevenue.setRegisterSubMonthRevenue(registerSubMonthRevenue == null ? 0 : registerSubMonthRevenue);
      statisticRevenue.setBuyPackageRevenue(buyPackageRevenue == null ? 0 : buyPackageRevenue);
      statisticRevenue.setFeeSubDayRevenue(feeSubDayRevenue == null ? 0 : feeSubDayRevenue);
      statisticRevenue.setFeeSubWeekRevenue(feeSubWeekRevenue == null ? 0 : feeSubWeekRevenue);
      statisticRevenue.setFeeSubMonthRevenue(feeSubMonthRevenue == null ? 0 : feeSubMonthRevenue);
      statisticRevenue.setAdsRevenue(adsRevenue == null ? 0 : adsRevenue);
      statisticRevenue.setTotalRevenue(totalRevenue == null ? 0 : totalRevenue);
      statisticRevenueStorage.save(statisticRevenue);
      statisticCheckpointStorage.save(statisticCheckpoint);
    }
  }

  public byte[] getRevenueStatisticReport(String startDate, String endDate) throws IOException {
    LocalDate start = DateUtils.convertStringToLocalDate(startDate);
    LocalDate end = DateUtils.convertStringToLocalDate(endDate);
    LocalDate nowAtVN = DateUtils.getNowDateAtUtc();
    if (start.isAfter(nowAtVN)) {
      throw new BadRequestException("End day must equal or greater than today");
    }
    List<StatisticRevenue> statisticRevenues = new ArrayList<>();
    for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
      StatisticRevenue statisticRevenue = statisticRevenueStorage.findByDate(date);
      if (statisticRevenue != null) {
        statisticRevenues.add(statisticRevenue);
      }
    }
    List<RevenueExcelResponse> revenueExcelResponses = modelMapper.toRevenueExcelResponses(statisticRevenues);
    return ExcelUtils.createExcelFile(revenueExcelResponses, RevenueExcelResponse.getHeader());
  }
}
