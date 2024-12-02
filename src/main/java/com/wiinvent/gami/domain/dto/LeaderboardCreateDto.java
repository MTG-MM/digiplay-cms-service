package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LeaderboardCreateDto extends LeaderboardUpdateDto{
  @NotNull
  private String code;
}
