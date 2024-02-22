package com.managersystem.admin.handleRequest.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class ChooseRewardItemSegmentDto {
  @NotNull
  private List<Long> ids;
}
