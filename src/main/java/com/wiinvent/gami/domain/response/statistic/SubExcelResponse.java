package com.wiinvent.gami.domain.response.statistic;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SubExcelResponse {
  private LocalDate date;

  private Integer newSub;

  private Integer subDay;

  private Integer subWeek;

  private Integer subMonth;

  private Integer totalSub;

  private Long subRevenue;

  private Integer subDayUser;

  private Integer subWeekUser;

  private Integer subMonthUser;

  private Integer unsubDayUser;

  private Integer unsubWeekUser;

  private Integer unsubMonthUser;

  private Integer unsubDayExpired;

  private Integer unsubWeekExpired;

  private Integer unsubMonthExpired;

  private Integer unsubDayManual;

  private Integer unsubWeekManual;

  private Integer unsubMonthManual;

  private Integer totalUnsub;

  public static String[] getHeader(){
    return new String[]{"date", "dailyActiveUser",
        "monthlyActiveUser", "newRegisterUser", "revenuePerUser", "paidUser", "revenuePerPaidUser"};
  }
}
