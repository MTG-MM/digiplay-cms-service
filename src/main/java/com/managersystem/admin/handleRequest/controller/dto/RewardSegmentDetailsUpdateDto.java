package com.managersystem.admin.handleRequest.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardSegmentDetailsUpdateDto {
  @NotNull
  protected Long rewardItemId;
  
  protected Long priority = 0L; //do uu tien nhan qua theo ti le

  protected Long segmentRate = 0L; //do uu tien nhan qua theo loai nguoi dung

  protected Long position;
}
