package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.PeriodType;
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
