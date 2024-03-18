package com.wiinvent.gami.app.controller.dto;

import com.wiinvent.gami.domain.entities.type.PeriodType;
import lombok.Data;

@Data
public class RewardScheduleUpdateDto {

  private PeriodType periodType;

  private Boolean isAccumulative;

  private Long quantity;

  private Long startAt;

  private Long endAt;
}
