package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.PeriodLimitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardSegmentDetailDto extends RewardSegmentDetailUpdateDto {

  @NotNull(message = "ID phần thưởng không được để trống")
  @Schema(description = "ID phần thưởng")
  private Long rewardItemId;

  @NotNull(message = "Loại giới hạn khoảng thời gian không được để trống")
  @Schema(description = "Loại giới hạn khoảng thời gian")
  private PeriodLimitType periodType;

  @NotNull(message = "Giá trị giới hạn khoảng thời gian không được để trống")
  @Schema(description = "Giá trị giới hạn khoảng thời gian")
  @Min(0)
  private Integer periodValue;

  @Schema(description = "Mặc định")
  private Boolean isDefault;
}
