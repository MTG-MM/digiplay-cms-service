package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameCategoryUpdateStatusDto {
  @NotNull(message = "không được để trống")
  private Integer id;

  @NotNull(message = "không được để trống")
  private Status status;
}
