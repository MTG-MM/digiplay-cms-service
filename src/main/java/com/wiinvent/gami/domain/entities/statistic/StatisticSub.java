package com.wiinvent.gami.domain.entities.statistic;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "statistic_sub")
public class StatisticSub extends BaseEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "date", nullable = false, length = 20)
  private LocalDate date;

  @Column(name = "new_sub")
  private Integer newSub;

  @Column(name = "sub_day")
  private Integer subDay;

  @Column(name = "sub_week")
  private Integer subWeek;

  @Column(name = "sub_month")
  private Integer subMonth;

  @Column(name = "total_sub")
  private Integer totalSub;

  @Column(name = "sub_revenue")
  private Long subRevenue;

  @Column(name = "sub_day_user")
  private Integer subDayUser;

  @Column(name = "sub_week_user")
  private Integer subWeekUser;

  @Column(name = "sub_month_user")
  private Integer subMonthUser;

  @Column(name = "unsub_day_user")
  private Integer unsubDayUser;

  @Column(name = "unsub_week_user")
  private Integer unsubWeekUser;

  @Column(name = "unsub_month_user")
  private Integer unsubMonthUser;

  @Column(name = "unsub_day_expired")
  private Integer unsubDayExpired;

  @Column(name = "unsub_week_expired")
  private Integer unsubWeekExpired;

  @Column(name = "unsub_month_expired")
  private Integer unsubMonthExpired;

  @Column(name = "unsub_day_manual")
  private Integer unsubDayManual;

  @Column(name = "unsub_week_manual")
  private Integer unsubWeekManual;

  @Column(name = "unsub_month_manual")
  private Integer unsubMonthManual;

  @Column(name = "total_unsub")
  private Integer totalUnsub;
}
