package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.QuestStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "quest_user_history")
public class QuestUserHistory extends BaseEntity{
  @Id
  @Column(name = "id", nullable = false, length = 16)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "quest_id")
  private Long questId;

  @Column(name = "user_id", length = 16)
  private UUID userId;

  @Column(name = "quest_turn_id")
  private Long questTurnId;

  @Enumerated(EnumType.STRING)
  @Column(name = "quest_state", length = 50)
  private QuestStatusType questState;

  @Column(name = "score")
  private Integer score;

  @Column(name = "completed_at")
  private Long completedAt;

  @Column(name = "end_at")
  private Long endAt;

  @Column(name = "date", length = 50)
  private LocalDate date;
}
