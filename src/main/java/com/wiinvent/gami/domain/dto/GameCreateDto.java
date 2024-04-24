package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameCreateDto extends GameUpdateDto {
  @NotNull(message = "Không được để trống")
  private UUID teamId;
}
