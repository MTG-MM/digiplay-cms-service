package com.wiinvent.gami.domain.entities.statistic;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "statistic_user")
public class StatisticUser extends BaseEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "date", nullable = false, length = 20)
  private LocalDate date;

  @Column(name = "daily_active_user")
  private Integer dailyActiveUser;

  @Column(name = "month_active_user")
  private Integer monthActiveUser;

  @Column(name = "new_register_user")
  private Integer newRegisterUser;

  @Column(name = "revenue_per_user")
  private Long revenuePerUser;

  @Column(name = "paid_user")
  private Integer paidUser;

  @Column(name = "revenue_per_paid_user")
  private Long revenuePerPaidUser;
}
