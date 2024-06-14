package com.wiinvent.gami.domain.entities.leaderboard;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.RewardStateType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "leaderboard_event")
public class LeaderboardEvent extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "start_at")
  private Long startAt;

  @Column(name = "end_at")
  private Long endAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "reward_status", length = 50)
  private RewardStateType rewardStatus;

  @Size(max = 50)
  @Column(name = "code", length = 50)
  private String code;

}