package com.wiinvent.gami.domain.service.statistic;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.statistic.StatisticCheckpoint;
import com.wiinvent.gami.domain.entities.statistic.StatisticRevenue;
import com.wiinvent.gami.domain.entities.statistic.StatisticSub;
import com.wiinvent.gami.domain.entities.statistic.StatisticUser;
import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.statistic.StatisticRevenueResponse;
import com.wiinvent.gami.domain.response.statistic.StatisticSubResponse;
import com.wiinvent.gami.domain.response.statistic.SubExcelResponse;
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
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class StatisticSubService extends BaseService {

  public StatisticSubResponse getStatisticSub2(String startDate, String endDate) {
    StatisticSubResponse response = new StatisticSubResponse();
    LocalDate start = DateUtils.convertStringToLocalDate(startDate);
    LocalDate end = DateUtils.convertStringToLocalDate(endDate);
    Long millisStartToday = DateUtils.getStartOfDay(start);
    Long millisEndToday = DateUtils.getEndOfDay(end);
    List<Package> dayPackages = packageStorage.findPackagesByDaySub(1);
    List<Package> weekPackages = packageStorage.findPackagesByDaySub(7);
    List<Package> monthPackages = packageStorage.findPackagesByDaySub(30);
    Integer totalUserSubDay = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
        , dayPackages.stream().map(Package::getCode).toList());
    Integer totalUserSubWeek = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
        , weekPackages.stream().map(Package::getCode).toList());
    Integer totalUserSubMonth = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
        , monthPackages.stream().map(Package::getCode).toList());
    Integer unsubDayUser = 0;
    Integer unsubWeekUser = 0;
    Integer unsubMonthUser = 0;
    StatisticSubResponse.StatisticTotal statisticTotal = response.getStatisticTotal();
    statisticTotal.setSubDayUser(totalUserSubDay);
    statisticTotal.setSubWeekUser(totalUserSubWeek);
    statisticTotal.setSubMonthUser(totalUserSubMonth);
    statisticTotal.setUnsubDayUser(unsubDayUser);
    statisticTotal.setUnsubWeekUser(unsubWeekUser);
    statisticTotal.setUnsubMonthUser(unsubMonthUser);
    for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
      StatisticSub statisticSub = statisticSubStorage.findByDate(current);
      StatisticSubResponse.StatisticDaily statisticDaily = new StatisticSubResponse.StatisticDaily(current.toString(), statisticSub);
      response.addStatistic(statisticDaily);
    }
    return response;
  }

  public StatisticSubResponse getStatisticSub(String startDate, String endDate) {
    StatisticSubResponse response = new StatisticSubResponse();
    LocalDate start = DateUtils.convertStringToLocalDate(startDate);
    LocalDate end = DateUtils.convertStringToLocalDate(endDate);
    Long millisStartToday = DateUtils.getStartOfDay(start);
    Long millisEndToday = DateUtils.getEndOfDay(end);

    List<Package> dayPackages = packageStorage.findPackagesByDaySub(1);
    List<Package> weekPackages = packageStorage.findPackagesByDaySub(7);
    List<Package> monthPackages = packageStorage.findPackagesByDaySub(30);

    CompletableFuture<Integer> totalUserSubDayFuture = countUserSubByPackageCodeAsync(millisStartToday, millisEndToday, dayPackages);
    CompletableFuture<Integer> totalUserSubWeekFuture = countUserSubByPackageCodeAsync(millisStartToday, millisEndToday, weekPackages);
    CompletableFuture<Integer> totalUserSubMonthFuture = countUserSubByPackageCodeAsync(millisStartToday, millisEndToday, monthPackages);

    CompletableFuture<List<StatisticSubResponse.StatisticDaily>> dailyStatisticsFuture = CompletableFuture.supplyAsync(() -> {
      List<StatisticSubResponse.StatisticDaily> dailyStatistics = new ArrayList<>();
      for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
        StatisticSub statisticSub = statisticSubStorage.findByDate(current);
        StatisticSubResponse.StatisticDaily dailyData = new StatisticSubResponse.StatisticDaily(current.toString(), statisticSub);
        dailyStatistics.add(dailyData);
      }
      return dailyStatistics;
    });

    CompletableFuture<Void> allFutures = CompletableFuture.allOf(
        totalUserSubDayFuture, totalUserSubWeekFuture, totalUserSubMonthFuture, dailyStatisticsFuture);

    return allFutures.thenApply(v -> {
      Integer totalUserSubDay = totalUserSubDayFuture.join();
      Integer totalUserSubWeek = totalUserSubWeekFuture.join();
      Integer totalUserSubMonth = totalUserSubMonthFuture.join();
      List<StatisticSubResponse.StatisticDaily> dailyStatistics = dailyStatisticsFuture.join();

      Integer unsubDayUser = 0;
      Integer unsubWeekUser = 0;
      Integer unsubMonthUser = 0;

      StatisticSubResponse.StatisticTotal statisticTotal = response.getStatisticTotal();
      statisticTotal.setSubDayUser(totalUserSubDay);
      statisticTotal.setSubWeekUser(totalUserSubWeek);
      statisticTotal.setSubMonthUser(totalUserSubMonth);
      statisticTotal.setUnsubDayUser(unsubDayUser);
      statisticTotal.setUnsubWeekUser(unsubWeekUser);
      statisticTotal.setUnsubMonthUser(unsubMonthUser);
      response.setStatisticDailies(dailyStatistics);
      return response;
    }).join();
  }

  private CompletableFuture<Integer> countUserSubByPackageCodeAsync(Long startDate, Long endDate, List<Package> packages) {
    List<String> packageCodes = packages.stream().map(Package::getCode).toList();
    return CompletableFuture.supplyAsync(() ->
        packageHistoryStorage.countUserSubByPackageCode(startDate, endDate, packageCodes));
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
        , weekPackages.stream().map(Package::getCode).toList());
    Integer userSubMonth = packageHistoryStorage.countUserSubByPackageCode(millisStartToday, millisEndToday
        , monthPackages.stream().map(Package::getCode).toList());
    Integer unsubDayUser = 0;
    Integer unsubWeekUser = 0;
    Integer unsubMonthUser = 0;
    Integer unsubDayExpired = 0;
    Integer unsubWeekExpired = 0;
    Integer unsubMonthExpired = 0;
    Integer unsubDayManual = 0;
    Integer unsubWeekManual = 0;
    Integer unsubMonthManual = 0;
    Integer totalSub = 0;
    Integer totalUnsub = 0;
    statisticSub.setNewSub(newSub == null ? 0 : newSub);
    statisticSub.setSubRevenue(subRevenue == null ? 0 : subRevenue);
    statisticSub.setSubDay(subDay == null ? 0 : subDay);
    statisticSub.setSubWeek(subWeek == null ? 0 : subWeek);
    statisticSub.setSubMonth(subMonth == null ? 0 : subMonth);
    statisticSub.setSubDayUser(userSubDay == null ? 0 : userSubDay);
    statisticSub.setSubWeekUser(userSubWeek == null ? 0 : userSubWeek);
    statisticSub.setSubMonthUser(userSubMonth == null ? 0 : userSubMonth);
    statisticSub.setUnsubDayUser(unsubDayUser == null ? 0 : unsubDayUser);
    statisticSub.setUnsubWeekUser(unsubWeekUser == null ? 0 : unsubWeekUser);
    statisticSub.setUnsubMonthUser(unsubMonthUser == null ? 0 : unsubMonthUser);
    statisticSub.setUnsubDayExpired(unsubDayExpired == null ? 0 : unsubDayExpired);
    statisticSub.setUnsubWeekExpired(unsubWeekExpired == null ? 0 : unsubWeekExpired);
    statisticSub.setUnsubMonthExpired(unsubMonthExpired == null ? 0 : unsubMonthExpired);
    statisticSub.setUnsubDayManual(unsubDayManual == null ? 0 : unsubDayManual);
    statisticSub.setUnsubWeekManual(unsubWeekManual == null ? 0 : unsubWeekManual);
    statisticSub.setUnsubMonthManual(unsubMonthManual == null ? 0 : unsubMonthManual);
    statisticSub.setTotalSub(totalSub == null ? 0 : totalSub);
    statisticSub.setTotalUnsub(totalUnsub == null ? 0 : totalUnsub);
    statisticSubStorage.save(statisticSub);
  }

  public void updateStatisticSubPreDay() {
    LocalDate dateNow = DateUtils.nowDate();
    StatisticCheckpoint statisticCheckpoint = statisticCheckpointStorage.findById(Constants.STATISTIC_SUB_CHECK_POINT_ID);
    if (statisticCheckpoint == null) {
      statisticCheckpoint = new StatisticCheckpoint();
      statisticCheckpoint.setId(Constants.STATISTIC_SUB_CHECK_POINT_ID);
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
      Integer unsubDayUser = 0;
      Integer unsubWeekUser = 0;
      Integer unsubMonthUser = 0;
      Integer unsubDayExpired = 0;
      Integer unsubWeekExpired = 0;
      Integer unsubMonthExpired = 0;
      Integer unsubDayManual = 0;
      Integer unsubWeekManual = 0;
      Integer unsubMonthManual = 0;
      Integer totalSub = 0;
      Integer totalUnsub = 0;
      statisticSub.setNewSub(newSub == null ? 0 : newSub);
      statisticSub.setSubRevenue(subRevenue == null ? 0 : subRevenue);
      statisticSub.setSubDay(subDay == null ? 0 : subDay);
      statisticSub.setSubWeek(subWeek == null ? 0 : subWeek);
      statisticSub.setSubMonth(subMonth == null ? 0 : subMonth);
      statisticSub.setSubDayUser(userSubDay == null ? 0 : userSubDay);
      statisticSub.setSubWeekUser(userSubWeek == null ? 0 : userSubWeek);
      statisticSub.setSubMonthUser(userSubMonth == null ? 0 : userSubMonth);
      statisticSub.setUnsubDayUser(unsubDayUser == null ? 0 : unsubDayUser);
      statisticSub.setUnsubWeekUser(unsubWeekUser == null ? 0 : unsubWeekUser);
      statisticSub.setUnsubMonthUser(unsubMonthUser == null ? 0 : unsubMonthUser);
      statisticSub.setUnsubDayExpired(unsubDayExpired == null ? 0 : unsubDayExpired);
      statisticSub.setUnsubWeekExpired(unsubWeekExpired == null ? 0 : unsubWeekExpired);
      statisticSub.setUnsubMonthExpired(unsubMonthExpired == null ? 0 : unsubMonthExpired);
      statisticSub.setUnsubDayManual(unsubDayManual == null ? 0 : unsubDayManual);
      statisticSub.setUnsubWeekManual(unsubWeekManual == null ? 0 : unsubWeekManual);
      statisticSub.setUnsubMonthManual(unsubMonthManual == null ? 0 : unsubMonthManual);
      statisticSub.setTotalSub(totalSub == null ? 0 : totalSub);
      statisticSub.setTotalUnsub(totalUnsub == null ? 0 : totalUnsub);
      statisticSubStorage.save(statisticSub);
      statisticCheckpointStorage.save(statisticCheckpoint);
    }
  }

  public byte[] getSubStatisticReport(String startDate, String endDate) throws IOException {
    LocalDate start = DateUtils.convertStringToLocalDate(startDate);
    LocalDate end = DateUtils.convertStringToLocalDate(endDate);
    LocalDate nowAtVN = DateUtils.getNowDateAtUtc();
    if (start.isAfter(nowAtVN)) {
      throw new BadRequestException("End day must equal or greater than today");
    }
    List<StatisticSub> statisticSubs = new ArrayList<>();
    for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
      StatisticSub statisticSub = statisticSubStorage.findByDate(date);
      if (statisticSub != null) {
        statisticSubs.add(statisticSub);
      }
    }
    List<SubExcelResponse> subExcelResponses = modelMapper.toSubExcelResponses(statisticSubs);
    return ExcelUtils.createExcelFile(subExcelResponses, SubExcelResponse.getHeader());
  }
}
