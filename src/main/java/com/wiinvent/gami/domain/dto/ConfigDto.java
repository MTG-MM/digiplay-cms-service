package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConfigDto {
  @NotNull
  @Size(min = 1, max = 200)
  private String key;

  private String value;
}
