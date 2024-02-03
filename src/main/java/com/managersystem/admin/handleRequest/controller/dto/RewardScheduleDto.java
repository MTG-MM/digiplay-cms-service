package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.PeriodType;
import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import lombok.Data;

@Data
public class RewardScheduleDto {

  private Long rewardSegmentDetailId;

  private PeriodType periodType;

  private Long quantity;

  private Long startAt;

  private Long endAt;
}
