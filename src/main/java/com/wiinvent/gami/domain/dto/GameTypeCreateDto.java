package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameTypeStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameTypeCreateDto {
  @NotEmpty(message = "không được để trống")
  private String name;

  private String description;

  @NotNull
  private GameTypeStatus status;
}