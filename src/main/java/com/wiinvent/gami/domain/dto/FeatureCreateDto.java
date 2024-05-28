package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.FeatureCode;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeatureCreateDto {
  @NotNull(message = "Không được để trống")
  private String name;

  @NotNull(message = "Không được để trống")
  private Integer levelUnlock;

  @NotNull(message = "Không được để trống")
  private FeatureCode code;

  @NotNull
  private Status status;
}