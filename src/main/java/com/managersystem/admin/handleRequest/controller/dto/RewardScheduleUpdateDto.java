package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.PeriodType;
import lombok.Data;

@Data
public class RewardScheduleUpdateDto {

  private PeriodType periodType;

  private Boolean isAccumulative;

  private Long quantity;

  private Long startAt;

  private Long endAt;
}
