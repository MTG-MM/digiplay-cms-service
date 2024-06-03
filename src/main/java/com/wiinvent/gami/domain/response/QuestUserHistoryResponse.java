package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.QuestStatusType;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class QuestUserHistoryResponse {
  private UUID id;
  private Long completedAt;
  private Long endAt;
  private Long questId;
  private Long questTurnId;
  private String questName;
  private String questTurnName;
  private LocalDate date;
  private Integer score;
  private QuestStatusType questState;
  private Long createdAt;
}
