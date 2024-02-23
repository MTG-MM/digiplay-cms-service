package com.managersystem.admin.handleRequest.controller.response;

import com.managersystem.admin.server.entities.type.PeriodLimitType;
import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RewardSegmentDetailResponse {

  private Long id;

  private Long priority; //do uu tien nhan qua theo ti le

  private Long segmentRate; //do uu tien nhan qua theo loai nguoi dung

  private Long position;

  private Long rewardItemId;

  private Long rewardSegmentId;

  private PeriodLimitType periodType;

  private Long periodNumber = 1L;

  private Long periodValue = 1L;

  private Integer quantityInPoll = 0;

  private String rewardName;

  private Boolean isLimited = false;
}
