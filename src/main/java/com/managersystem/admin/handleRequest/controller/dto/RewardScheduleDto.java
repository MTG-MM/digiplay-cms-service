package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.PeriodType;
import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RewardScheduleDto extends RewardScheduleUpdateDto{

  private Long rewardSegmentDetailId;
}
