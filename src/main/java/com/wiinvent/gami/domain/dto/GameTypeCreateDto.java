package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameTypeStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GameTypeCreateDto {
  @NotEmpty(message = "không được để trống")
  private String name;

  private String description;

  private GameTypeStatus status;
}