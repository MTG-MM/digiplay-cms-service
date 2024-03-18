package com.wiinvent.gami.app.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RewardScheduleDto extends RewardScheduleUpdateDto{

  private Long rewardSegmentDetailId;
}
