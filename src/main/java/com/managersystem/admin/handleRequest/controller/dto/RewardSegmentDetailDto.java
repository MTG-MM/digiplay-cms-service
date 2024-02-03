package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import lombok.Data;

@Data
public class RewardSegmentDetailDto {

  private Long id;

  private Long priority; //do uu tien nhan qua theo ti le

  private Long segmentRate; //do uu tien nhan qua theo loai nguoi dung

  private Long position;

  private Long rewardItemId;

  private Long rewardSegmentId;
}
