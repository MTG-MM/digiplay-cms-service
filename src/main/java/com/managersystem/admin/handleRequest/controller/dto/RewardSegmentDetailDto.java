package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.PeriodLimitType;
import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardSegmentDetailDto extends RewardSegmentDetailUpdateDto{
//
//  private Long rewardItemId;
//
//  private Long rewardSegmentId;

  @NotNull
  private Long rewardItemId;

  @NotNull
  private PeriodLimitType periodType;

  @NotNull
  private Integer periodValue;

  private Boolean isDefault;

  @NotNull
  @Min(0)
  private Long priority;

  @NotNull
  private Long segmentRate;

  @NotNull
  private Long position;
}
