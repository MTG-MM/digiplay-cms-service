package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameCategoryCreateDto {
  @NotNull(message = "không được để trống")
  private String name;

  @NotNull
  private Status status;

  private GameType categoryType = GameType.FREE;

  private Boolean isRequireSub = false;

  private Integer point = 0;

  private Integer coin = 0;

  private String description;
}
