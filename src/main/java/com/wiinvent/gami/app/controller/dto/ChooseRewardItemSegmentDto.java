package com.wiinvent.gami.app.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class ChooseRewardItemSegmentDto {
  @NotNull
  private List<Long> ids;
}
