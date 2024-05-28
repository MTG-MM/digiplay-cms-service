package com.wiinvent.gami.domain.response.statistic;

import com.wiinvent.gami.domain.entities.statistic.StatisticRevenue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatisticRevenueResponse {
  StatisticTotal statisticTotal = new StatisticTotal();
  List<StatisticDaily> statisticDailies = new ArrayList<>();

  @Data
  public static class StatisticDaily {
    private String daily;
    private Long buyPackageRevenue = 0L;
    private Long registerSubDayRevenue = 0L;
    private Long registerSubWeekRevenue = 0L;
    private Long registerSubMonthRevenue = 0L;


    public StatisticDaily(String date, StatisticRevenue statisticRevenue) {
      if (statisticRevenue == null) {
        daily = date;
        return;
      }
      daily = statisticRevenue.getDate().toString();
      buyPackageRevenue = statisticRevenue.getBuyPackageRevenue();
      registerSubDayRevenue = statisticRevenue.getRegisterSubDayRevenue();
      registerSubWeekRevenue = statisticRevenue.getRegisterSubWeekRevenue();
      registerSubMonthRevenue = statisticRevenue.getRegisterSubMonthRevenue();
    }
  }

  @Data
  public static class StatisticTotal {
    private Long buyPackageRevenue = 0L;
    private Long registerSubDayRevenue = 0L;
    private Long registerSubWeekRevenue = 0L;
    private Long registerSubMonthRevenue = 0L;
  }

  public void addStatistic(StatisticDaily statisticDaily) {
    statisticTotal.buyPackageRevenue += statisticDaily.getBuyPackageRevenue();
    statisticTotal.registerSubDayRevenue += statisticDaily.getRegisterSubDayRevenue();
    statisticTotal.registerSubWeekRevenue += statisticDaily.getRegisterSubWeekRevenue();
    statisticTotal.registerSubMonthRevenue += statisticDaily.getRegisterSubMonthRevenue();
    statisticDailies.add(statisticDaily);
  }
}
