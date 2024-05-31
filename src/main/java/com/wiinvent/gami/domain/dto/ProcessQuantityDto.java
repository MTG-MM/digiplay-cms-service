package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessQuantityDto {
  @NotNull
  private Long id;
  @Min(1)
  private Long quantity;
  @NotNull
  private ProcessQuantityType type;

  public enum ProcessQuantityType {
    ADD,
    SUBTRACT
  }
}
