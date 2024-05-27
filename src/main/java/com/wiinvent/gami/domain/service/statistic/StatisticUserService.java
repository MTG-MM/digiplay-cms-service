package com.wiinvent.gami.domain.service.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticCheckpoint;
import com.wiinvent.gami.domain.entities.statistic.StatisticUser;
import com.wiinvent.gami.domain.response.statistic.StatisticUserResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Log4j2
public class StatisticUserService extends BaseService {

  public StatisticUserResponse getStatisticUser(String startDate, String endDate) {
    StatisticUserResponse response = new StatisticUserResponse();
    LocalDate start = DateUtils.convertStringToLocalDate(startDate);
    LocalDate end = DateUtils.convertStringToLocalDate(endDate);

    for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
      StatisticUser statisticUser = statisticUserStorage.findByDate(current);
      StatisticUserResponse.StatisticDaily statisticDaily = new StatisticUserResponse.StatisticDaily(current.toString(), statisticUser);
      response.addStatistic(statisticDaily);
    }
    return response;
  }

  public void updateStatisticUserToday() {
    LocalDate dateNow = DateUtils.nowDate();
    Long millisStartToday = DateUtils.getStartOfDay(dateNow);
    Long millisEndToday = DateUtils.getStartOfDay(dateNow.plusDays(1)) - 1;
    StatisticUser statisticUser = statisticUserStorage.findByDate(dateNow);
    if (statisticUser == null) {
      statisticUser = new StatisticUser();
      statisticUser.setDate(dateNow);
      statisticUser.setCreatedAt(DateUtils.getNowMillisAtUtc());
    }

    Integer nru = userStorage.countNewRegisterUser(millisStartToday, millisEndToday);
    Integer dau = userStorage.countDailyActiveUser(millisStartToday, millisEndToday);
    Integer mau = userStorage.countMonthlyActiveUser(dateNow);
    Integer paidUser = packageHistoryStorage.countTotalPaidUser(millisStartToday, millisEndToday);
    Long totalRevenue = packageHistoryStorage.countTotalRevenue(millisStartToday, millisEndToday);
    Long revenuePerUser = totalRevenue % dau;
    Long revenuePerPaidUser = totalRevenue % paidUser;

    statisticUser.setNewRegisterUser(nru == null ? 0 : nru);
    statisticUser.setDailyActiveUser(dau);
    statisticUser.setMonthActiveUser(mau == null ? 0 : mau);
    statisticUser.setPaidUser(paidUser);
    statisticUser.setRevenuePerUser(revenuePerUser);
    statisticUser.setRevenuePerPaidUser(revenuePerPaidUser);
    statisticUserStorage.save(statisticUser);
  }

  public void updateStatisticUserPreDay() {
    LocalDate dateNow = DateUtils.nowDate();
    StatisticCheckpoint statisticCheckpoint = statisticCheckpointStorage.findById(Constants.STATISTIC_USER_CHECK_POINT_ID);
    if (statisticCheckpoint == null) {
      statisticCheckpoint = new StatisticCheckpoint();
      statisticCheckpoint.setCheckPoint(dateNow.minusDays(30));
    }

    for (LocalDate current = statisticCheckpoint.getCheckPoint(); current.isBefore(dateNow); current = current.plusDays(1)) {
      Long millisStartToday = DateUtils.getStartOfDay(current);
      Long millisEndToday = DateUtils.getStartOfDay(current.plusDays(1)) - 1;
      StatisticUser statisticUser = statisticUserStorage.findByDate(current);
      if (statisticUser == null) {
        statisticUser = new StatisticUser();
        statisticUser.setDate(current);
      }

      Integer nru = userStorage.countNewRegisterUser(millisStartToday, millisEndToday);
      Integer dau = userStorage.countDailyActiveUser(millisStartToday, millisEndToday);
      Integer mau = userStorage.countMonthlyActiveUser(dateNow);
      Integer paidUser = packageHistoryStorage.countTotalPaidUser(millisStartToday, millisEndToday);
      Long totalRevenue = packageHistoryStorage.countTotalRevenue(millisStartToday, millisEndToday);
      Long revenuePerUser = totalRevenue % dau;
      Long revenuePerPaidUser = totalRevenue % paidUser;

      statisticUser.setNewRegisterUser(nru == null ? 0 : nru);
      statisticUser.setDailyActiveUser(dau);
      statisticUser.setMonthActiveUser(mau == null ? 0 : mau);
      statisticUser.setPaidUser(paidUser);
      statisticUser.setRevenuePerUser(revenuePerUser);
      statisticUser.setRevenuePerPaidUser(revenuePerPaidUser);
      statisticUserStorage.save(statisticUser);
      statisticCheckpointStorage.save(statisticCheckpoint);
    }
  }
}
