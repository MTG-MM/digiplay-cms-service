package com.managersystem.admin.handleRequest.controller.response;

import com.managersystem.admin.server.entities.type.PeriodType;
import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RewardScheduleResponse {

  private Long id;

  private Long rewardSegmentDetailId;

  private PeriodType periodType;

  private Boolean isAccumulative;

  private Long quantity;

  private Long startAt;

  private Long endAt;
}
