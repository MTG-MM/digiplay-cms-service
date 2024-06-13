package com.wiinvent.gami.domain.response.statistic;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserExcelResponse {
  private LocalDate date;

  private Integer dailyActiveUser;

  private Integer monthlyActiveUser;

  private Integer newRegisterUser;

  private Long revenuePerUser;

  private Integer paidUser;

  private Long revenuePerPaidUser;

  public static String[] getHeader() {
    return new String[]{"date", "dailyActiveUser",
        "monthlyActiveUser", "newRegisterUser", "revenuePerUser", "paidUser", "revenuePerPaidUser"};
  }
}
