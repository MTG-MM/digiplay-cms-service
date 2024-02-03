package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "reward_state_log")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardStateLog extends BaseEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "reward_state_id")
  private Long rewardStateId;

  @Column(name = "quantity_day")
  private Long quantityDay;

  @Column(name = "quantity_hour")
  private Long quantityHour;

  @Column(name = "quantity_minute")
  private Long quantityMinute;

  @Column(name = "created_time")
  private LocalDateTime createdTime;
}
