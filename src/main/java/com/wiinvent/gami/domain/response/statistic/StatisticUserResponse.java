package com.wiinvent.gami.domain.response.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatisticUserResponse {
  StatisticTotal statisticTotal = new StatisticTotal();
  List<StatisticDaily> statisticDailies = new ArrayList<>();

  @Data
  public static class StatisticDaily{
    private String daily;
    private Integer dailyActiveUser = 0;
    private Integer monthlyActiveUser = 0;
    private Integer newRegisterUser = 0;
    private Long revenuePerUser = 0L;
    private Long revenuePerPaidUser = 0L;
    private Integer paidUser = 0;

    public StatisticDaily(String date, StatisticUser statisticUser) {
      if (statisticUser == null) {
        daily = date;
        return;
      }
      daily = statisticUser.getDate().toString();
      dailyActiveUser = statisticUser.getDailyActiveUser();
      monthlyActiveUser = statisticUser.getMonthlyActiveUser();
      newRegisterUser = statisticUser.getNewRegisterUser();
      revenuePerUser = statisticUser.getRevenuePerUser();
      revenuePerPaidUser = statisticUser.getRevenuePerPaidUser();
      paidUser = statisticUser.getPaidUser();
    }
  }

  @Data
  public static class StatisticTotal {
    private Integer dailyActiveUser = 0;
    private Integer monthlyActiveUser = 0;
    private Integer newRegisterUser = 0;
    private Long revenuePerUser = 0L;
    private Long revenuePerPaidUser = 0L;
    private Integer paidUser = 0;
  }

  public void addStatistic(StatisticDaily statisticDaily) {
    statisticTotal.dailyActiveUser += statisticDaily.getDailyActiveUser();
    if (statisticDaily.monthlyActiveUser > statisticTotal.monthlyActiveUser) {
      statisticTotal.monthlyActiveUser = statisticDaily.monthlyActiveUser;
    }
    statisticTotal.newRegisterUser += statisticDaily.getNewRegisterUser();
    statisticTotal.revenuePerUser += statisticDaily.getRevenuePerUser();
    statisticTotal.revenuePerPaidUser += statisticDaily.getRevenuePerPaidUser();
    statisticTotal.paidUser += statisticDaily.getPaidUser();
    statisticDailies.add(statisticDaily);
  }
}
