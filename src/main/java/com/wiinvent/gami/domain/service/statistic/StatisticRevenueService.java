package com.wiinvent.gami.domain.service.statistic;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.statistic.StatisticCheckpoint;
import com.wiinvent.gami.domain.entities.statistic.StatisticRevenue;
import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.response.statistic.StatisticRevenueResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    statisticRevenue.setRegisterSubDayRevenue(registerSubDayRevenue == null ? 0 : registerSubDayRevenue);
    statisticRevenue.setRegisterSubWeekRevenue(registerSubWeekRevenue == null ? 0 : registerSubWeekRevenue);
    statisticRevenue.setRegisterSubMonthRevenue(registerSubMonthRevenue == null ? 0 : registerSubMonthRevenue);
    statisticRevenue.setBuyPackageRevenue(buyPackageRevenue == null ? 0 : buyPackageRevenue);
    statisticRevenueStorage.save(statisticRevenue);
  }

  public void updateStatisticRevenuePreDay() {
    LocalDate dateNow = DateUtils.nowDate();
    StatisticCheckpoint statisticCheckpoint = statisticCheckpointStorage.findById(Constants.STATISTIC_REVENUE_CHECK_POINT_ID);
    if (statisticCheckpoint == null) {
      statisticCheckpoint = new StatisticCheckpoint();
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

      statisticRevenue.setRegisterSubDayRevenue(registerSubDayRevenue == null ? 0 : registerSubDayRevenue);
      statisticRevenue.setRegisterSubWeekRevenue(registerSubWeekRevenue == null ? 0 : registerSubWeekRevenue);
      statisticRevenue.setRegisterSubMonthRevenue(registerSubMonthRevenue == null ? 0 : registerSubMonthRevenue);
      statisticRevenue.setBuyPackageRevenue(buyPackageRevenue == null ? 0 : buyPackageRevenue);
      statisticRevenueStorage.save(statisticRevenue);
      statisticCheckpointStorage.save(statisticCheckpoint);
    }
  }
}
