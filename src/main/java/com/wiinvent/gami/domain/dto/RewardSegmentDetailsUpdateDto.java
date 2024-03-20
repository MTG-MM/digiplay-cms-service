package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.PeriodLimitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardSegmentDetailsUpdateDto {

  @NotNull(message = "ID phần thưởng không được để trống")
  @Schema(description = "ID phần thưởng")
  protected Long rewardItemId;

  @Schema(description = "Độ ưu tiên nhận quà theo tỉ lệ", example = "0")
  protected Long priority = 0L;

  @Schema(description = "Độ ưu tiên nhận quà theo loại người dùng", example = "0")
  protected Long segmentRate = 0L;

  @Schema(description = "Vị trí")
  protected Long position;

  @Schema(description = "Là quà mặc định sẽ nhả ra nếu không còn quà", example = "false")
  private Boolean isDefault = false;

  @Schema(description = "Khoảng thời gian", example = "DAY")
  private PeriodLimitType periodType = PeriodLimitType.DAY;

  @Schema(description = "Số khoảng thời gian", example = "1")
  private Long periodNumber = 1L;

  @Schema(description = "Số quà tối đa người dùng có thể nhận trong khoảng thời gian", example = "1")
  private Long periodValue = 1L;
}
