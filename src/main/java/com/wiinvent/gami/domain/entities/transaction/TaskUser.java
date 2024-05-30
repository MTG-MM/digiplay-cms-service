package com.wiinvent.gami.domain.entities.transaction;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.QualifyRewardStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "user_gold_pig")
public class TaskUser extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false, length = 16)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "completed_at")
  private Long completedAt;

  @Column(name = "count")
  private Integer count;

  @Column(name = "date", length = 50)
  private LocalDate date;

  @Column(name = "task_id")
  private Integer taskId;

  @Column(name = "user_id", length = 16)
  private UUID userId;

  @Column(name = "score")
  private Integer score;

  @Enumerated(EnumType.STRING)
  @Column(name = "reward_status")
  private QualifyRewardStatus status;
}
