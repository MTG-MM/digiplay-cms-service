package com.wiinvent.gami.domain.response.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticSub;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatisticSubResponse {

  StatisticTotal statisticTotal = new StatisticTotal();
  List<StatisticDaily> statisticDailies = new ArrayList<>();

  @Data
  public static class StatisticDaily {
    private String daily;
    private Integer newSub = 0;
    private Integer subDay = 0;
    private Integer subWeek = 0;
    private Integer subMonth = 0;
    private Integer subDayUser = 0;
    private Integer subWeekUser = 0;
    private Integer subMonthUser = 0;
    private Integer unsubDayUser = 0;
    private Integer unsubWeekUser = 0;
    private Integer unsubMonthUser = 0;
    private Integer unsubDayExpired = 0;
    private Integer unsubWeekExpired = 0;
    private Integer unsubMonthExpired = 0;
    private Integer unsubDayManual = 0;
    private Integer unsubWeekManual = 0;
    private Integer unsubMonthManual = 0;
    private Integer totalSub = 0;
    private Integer totalUnsub = 0;
    private Long subRevenue = 0L;

    public StatisticDaily(String date, StatisticSub statisticSub) {
      if (statisticSub == null) {
        daily = date;
        return;
      }
      daily = statisticSub.getDate().toString();
      newSub = statisticSub.getNewSub();
      subDay = statisticSub.getSubDay();
      subWeek = statisticSub.getSubWeek();
      subMonth = statisticSub.getSubMonth();
      subDayUser = statisticSub.getSubDayUser();
      subWeekUser = statisticSub.getSubWeekUser();
      subMonthUser = statisticSub.getSubMonthUser();
      unsubDayUser = statisticSub.getUnsubDayUser();
      unsubWeekUser = statisticSub.getUnsubWeekUser();
      unsubMonthUser = statisticSub.getUnsubMonthUser();
      unsubDayExpired = statisticSub.getUnsubDayExpired();
      unsubWeekExpired = statisticSub.getUnsubWeekExpired();
      unsubMonthExpired = statisticSub.getUnsubMonthExpired();
      unsubDayManual = statisticSub.getUnsubDayManual();
      unsubWeekManual = statisticSub.getUnsubWeekManual();
      unsubMonthManual = statisticSub.getUnsubMonthManual();
      totalSub = statisticSub.getTotalSub();
      totalUnsub = statisticSub.getTotalUnsub();
      subRevenue = statisticSub.getSubRevenue();
    }
  }

  @Data
  public static class StatisticTotal {
    private Integer newSub = 0;
    private Integer subDay = 0;
    private Integer subWeek = 0;
    private Integer subMonth = 0;
    private Integer subDayUser = 0;
    private Integer subWeekUser = 0;
    private Integer subMonthUser = 0;
    private Integer unsubDayUser = 0;
    private Integer unsubWeekUser = 0;
    private Integer unsubMonthUser = 0;
    private Integer unsubDayExpired = 0;
    private Integer unsubWeekExpired = 0;
    private Integer unsubMonthExpired = 0;
    private Integer unsubDayManual = 0;
    private Integer unsubWeekManual = 0;
    private Integer unsubMonthManual = 0;
    private Integer totalSub = 0;
    private Integer totalUnsub = 0;
    private Long subRevenue = 0L;
  }

  public void addStatistic(StatisticDaily statisticDaily) {
    statisticTotal.newSub += statisticDaily.getNewSub();
    statisticTotal.subDay += statisticDaily.getSubDay();
    statisticTotal.subWeek += statisticDaily.getSubWeek();
    statisticTotal.subMonth += statisticDaily.getSubMonth();
//    statisticTotal.unsubDayUser += statisticDaily.getUnsubDayUser();
//    statisticTotal.unsubWeekUser += statisticDaily.getUnsubWeekUser();
//    statisticTotal.unsubMonthUser += statisticDaily.getUnsubMonthUser();
    statisticTotal.unsubDayExpired += statisticDaily.getUnsubDayExpired();
    statisticTotal.unsubWeekExpired += statisticDaily.getUnsubWeekExpired();
    statisticTotal.unsubMonthExpired += statisticDaily.getUnsubMonthExpired();
    statisticTotal.unsubDayManual += statisticDaily.getUnsubDayManual();
    statisticTotal.unsubWeekManual += statisticDaily.getUnsubWeekManual();
    statisticTotal.unsubMonthManual += statisticDaily.getUnsubMonthManual();
    statisticTotal.totalSub += statisticDaily.getTotalSub();
    statisticTotal.totalUnsub += statisticDaily.getTotalUnsub();
    statisticTotal.subRevenue += statisticDaily.getSubRevenue();
    statisticDailies.add(statisticDaily);
  }
}
