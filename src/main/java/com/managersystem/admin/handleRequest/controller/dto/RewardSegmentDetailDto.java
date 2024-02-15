package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import lombok.Data;

@Data
public class RewardSegmentDetailDto extends RewardSegmentDetailUpdateDto{

  private Long rewardItemId;

  private Long rewardSegmentId;
}
