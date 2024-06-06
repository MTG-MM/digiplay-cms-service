package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.QuestStatusType;
import lombok.Data;

import java.util.UUID;
@Data
public class InternalQuestRequestDto {
  private UUID questHistoryId;
  private QuestStatusType questState;
}
