package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class InternalAchievementRequestDto {
  @NotNull
  private UUID userId;
  @NotNull
  private Integer itemId;
  @NotNull
  private Integer score;
}
