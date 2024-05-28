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
    private Integer totalSub = 0;
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
      totalSub = statisticSub.getTotalSub();
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
    private Integer totalSub = 0;
    private Long subRevenue = 0L;
  }

  public void addStatistic(StatisticDaily statisticDaily) {
    statisticTotal.newSub += statisticDaily.getNewSub();
    statisticTotal.subDay += statisticDaily.getSubDay();
    statisticTotal.subWeek += statisticDaily.getSubWeek();
    statisticTotal.subMonth += statisticDaily.getSubMonth();
    statisticTotal.subDayUser += statisticDaily.getSubDayUser();
    statisticTotal.subWeekUser += statisticDaily.getSubWeekUser();
    statisticTotal.subMonthUser += statisticDaily.getSubMonthUser();
    statisticTotal.totalSub += statisticDaily.getTotalSub();
    statisticTotal.subRevenue += statisticDaily.getSubRevenue();
  }
}
