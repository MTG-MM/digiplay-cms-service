package com.wiinvent.gami.app.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class RewardScheduleDto extends RewardScheduleUpdateDto {

  @NotNull(message = "ID phần thưởng chi tiết không được để trống")
  @Schema(description = "ID phần thưởng chi tiết")
  private Long rewardSegmentDetailId;
}
