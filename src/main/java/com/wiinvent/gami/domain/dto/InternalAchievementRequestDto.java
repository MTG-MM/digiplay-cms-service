package com.wiinvent.gami.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InternalAchievementRequestDto {
  private Integer achievementId;
  private UUID userId;
  private Double score;
}
