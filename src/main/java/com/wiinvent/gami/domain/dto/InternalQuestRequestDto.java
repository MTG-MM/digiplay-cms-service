package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.QuestStatusType;
import lombok.Data;

import java.util.UUID;
@Data
public class InternalQuestRequestDto {
  private Integer questId;
  private UUID userId;
  private Integer score;
  private QuestStatusType questState;
}
