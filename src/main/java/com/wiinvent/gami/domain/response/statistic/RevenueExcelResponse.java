package com.wiinvent.gami.domain.response.statistic;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RevenueExcelResponse {
  private LocalDate date;

  private Long buyPackageRevenue;

  private Long registerSubDayRevenue;

  private Long registerSubWeekRevenue;

  private Long registerSubMonthRevenue;

  private Long feeSubDayRevenue;

  private Long feeSubWeekRevenue;

  private Long feeSubMonthRevenue;

  private Long adsRevenue;

  private Long totalRevenue;

  public static String[] getHeader() {
    return new String[]{"date", "buyPackageRevenue",
        "registerSubDayRevenue", "registerSubWeekRevenue", "registerSubMonthRevenue", "feeSubDayRevenue", "feeSubWeekRevenue",
        "feeSubMonthRevenue", "adsRevenue", "totalRevenue"};
  }
}
