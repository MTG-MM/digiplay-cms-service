package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class ChooseRewardItemSegmentDto {
  @NotNull
  private List<Long> ids;
}
