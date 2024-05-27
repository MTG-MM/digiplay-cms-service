package com.wiinvent.gami.domain.service.statistic;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.statistic.StatisticCheckpoint;
import com.wiinvent.gami.domain.entities.statistic.StatisticRevenue;
import com.wiinvent.gami.domain.entities.statistic.StatisticSub;
import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.response.statistic.StatisticRevenueResponse;
import com.wiinvent.gami.domain.response.statistic.StatisticSubResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
public class StatisticSubService extends BaseService {

  public StatisticSubResponse getStatisticSub(String startDate, String endDate) {
    StatisticSubResponse response = new StatisticSubResponse();
    LocalDate start = DateUtils.convertStringToLocalDate(startDate);
    LocalDate end = DateUtils.convertStringToLocalDate(endDate);

    for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
      StatisticSub statisticSub = statisticSubStorage.findByDate(current);
      StatisticSubResponse.StatisticDaily statisticDaily = new StatisticSubResponse.StatisticDaily(current.toString(), statisticSub);
      response.addStatistic(statisticDaily);
    }
    return response;
  }

  public void updateStatisticSubToday() {
    LocalDate dateNow = DateUtils.nowDate();
    Long millisStartToday = DateUtils.getStartOfDay(dateNow);
    Long millisEndToday = DateUtils.getStartOfDay(dateNow.plusDays(1)) - 1;
    StatisticSub statisticSub = statisticSubStorage.findByDate(dateNow);
    if (statisticSub == null) {
      statisticSub = new StatisticSub();
      statisticSub.setDate(dateNow);
      statisticSub.setCreatedAt(DateUtils.getNowMillisAtUtc());
    }

    List<Package> dayPackages = packageStorage.findPackagesByDaySub(1);
    List<Package> weekPackages = packageStorage.findPackagesByDaySub(7);
    List<Package> monthPackages = packageStorage.findPackagesByDaySub(30);
    List<Package> chargePackages = packageStorage.findPackagesByType(ProductPackageType.CHARGE);
    Integer newSub = packageHistoryStorage.countNewSub(millisStartToday, millisEndToday, chargePackages.stream().map(Package::getCode).toList());
    Long subRevenue = packageHistoryStorage.countTotalRevenueSub(millisStartToday, millisEndToday
        , chargePackages.stream().map(Package::getCode).toList());
    Integer subDay = packageHistoryStorage.countPackagePyPackageCode(millisStartToday, millisEndToday
        , dayPackages.stream().map(Package::getCode).toList());
    Integer subWeek = packageHistoryStorage.countPackagePyPackageCode(millisStartToday, millisEndToday
        , weekPackages.stream().map(Package::getCode).toList());
    Integer subMonth = packageHistoryStorage.countPackagePyPackageCode(millisStartToday, millisEndToday
        , monthPackages.stream().map(Package::getCode).toList());
    Integer userSubDay = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
        , dayPackages.stream().map(Package::getCode).toList());
    Integer userSubWeek = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
        , dayPackages.stream().map(Package::getCode).toList());
    Integer userSubMonth = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
        , dayPackages.stream().map(Package::getCode).toList());
    statisticSub.setNewSub(newSub == null ? 0 : newSub);
    statisticSub.setSubRevenue(subRevenue == null ? 0 : subRevenue);
    statisticSub.setSubDay(subDay == null ? 0 : subDay);
    statisticSub.setSubWeek(subWeek == null ? 0 : subWeek);
    statisticSub.setSubMonth(subMonth == null ? 0 : subMonth);
    statisticSub.setSubDayUser(userSubDay == null ? 0 : userSubDay);
    statisticSub.setSubWeekUser(userSubWeek == null ? 0 : userSubWeek);
    statisticSub.setSubMonthUser(userSubMonth == null ? 0 : userSubMonth);
    statisticSubStorage.save(statisticSub);
  }

  public void updateStatisticSubPreDay() {
    LocalDate dateNow = DateUtils.nowDate();
    StatisticCheckpoint statisticCheckpoint = statisticCheckpointStorage.findById(Constants.STATISTIC_SUB_CHECK_POINT_ID);
    if (statisticCheckpoint == null) {
      statisticCheckpoint = new StatisticCheckpoint();
      statisticCheckpoint.setCheckPoint(dateNow.minusDays(30));
    }

    for (LocalDate current = statisticCheckpoint.getCheckPoint(); current.isBefore(dateNow); current = current.plusDays(1)) {
      Long millisStartToday = DateUtils.getStartOfDay(current);
      Long millisEndToday = DateUtils.getStartOfDay(current.plusDays(1)) - 1;
      StatisticSub statisticSub = statisticSubStorage.findByDate(current);
      if (statisticSub == null) {
        statisticSub = new StatisticSub();
        statisticSub.setDate(current);
      }

      List<Package> dayPackages = packageStorage.findPackagesByDaySub(1);
      List<Package> weekPackages = packageStorage.findPackagesByDaySub(7);
      List<Package> monthPackages = packageStorage.findPackagesByDaySub(30);
      List<Package> chargePackages = packageStorage.findPackagesByType(ProductPackageType.CHARGE);
      Integer newSub = packageHistoryStorage.countNewSub(millisStartToday, millisEndToday, chargePackages.stream().map(Package::getCode).toList());
      Long subRevenue = packageHistoryStorage.countTotalRevenueSub(millisStartToday, millisEndToday
          , chargePackages.stream().map(Package::getCode).toList());
      Integer subDay = packageHistoryStorage.countPackagePyPackageCode(millisStartToday, millisEndToday
          , dayPackages.stream().map(Package::getCode).toList());
      Integer subWeek = packageHistoryStorage.countPackagePyPackageCode(millisStartToday, millisEndToday
          , weekPackages.stream().map(Package::getCode).toList());
      Integer subMonth = packageHistoryStorage.countPackagePyPackageCode(millisStartToday, millisEndToday
          , monthPackages.stream().map(Package::getCode).toList());
      Integer userSubDay = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
          , dayPackages.stream().map(Package::getCode).toList());
      Integer userSubWeek = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
          , dayPackages.stream().map(Package::getCode).toList());
      Integer userSubMonth = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
          , dayPackages.stream().map(Package::getCode).toList());
      statisticSub.setNewSub(newSub == null ? 0 : newSub);
      statisticSub.setSubRevenue(subRevenue == null ? 0 : subRevenue);
      statisticSub.setSubDay(subDay == null ? 0 : subDay);
      statisticSub.setSubWeek(subWeek == null ? 0 : subWeek);
      statisticSub.setSubMonth(subMonth == null ? 0 : subMonth);
      statisticSub.setSubDayUser(userSubDay == null ? 0 : userSubDay);
      statisticSub.setSubWeekUser(userSubWeek == null ? 0 : userSubWeek);
      statisticSub.setSubMonthUser(userSubMonth == null ? 0 : userSubMonth);
      statisticSubStorage.save(statisticSub);
      statisticCheckpointStorage.save(statisticCheckpoint);
    }
  }
}
