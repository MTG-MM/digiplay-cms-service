package com.wiinvent.gami.domain.entities.statistic;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "statistic_revenue")
public class StatisticRevenue extends BaseEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "date", nullable = false, length = 20)
  private LocalDate date;

  @Column(name = "buy_package_revenue")
  private Long buyPackageRevenue;

  @Column(name = "register_subday_revenue")
  private Long registerSubDayRevenue;

  @Column(name = "register_subweek_revenue")
  private Long registerSubWeekRevenue;

  @Column(name = "register_submonth_revenue")
  private Long registerSubMonthRevenue;

  @Column(name = "fee_subday_revenue")
  private Long feeSubDayRevenue;

  @Column(name = "fee_subweek_revenue")
  private Long feeSubWeekRevenue;

  @Column(name = "fee_submonth_revenue")
  private Long feeSubMonthRevenue;

  @Column(name = "ads_revenue")
  private Long adsRevenue;

  @Column(name = "total_revenue")
  private Long totalRevenue;
}
