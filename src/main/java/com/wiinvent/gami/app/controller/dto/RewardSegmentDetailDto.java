package com.wiinvent.gami.app.controller.dto;

import com.wiinvent.gami.domain.entities.type.PeriodLimitType;
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
}
