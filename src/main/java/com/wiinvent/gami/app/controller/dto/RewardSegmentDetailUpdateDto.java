package com.wiinvent.gami.app.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardSegmentDetailUpdateDto {

  @NotNull(message = "Độ ưu tiên không được để trống")
  @Min(value = 0, message = "Độ ưu tiên phải lớn hơn hoặc bằng 0")
  @Schema(description = "Độ ưu tiên nhận quà theo tỉ lệ")
  protected Long priority;

  @NotNull(message = "Độ ưu tiên không được để trống")
  @Min(value = 0, message = "Độ ưu tiên phải lớn hơn hoặc bằng 0")
  @Schema(description = "Độ ưu tiên nhận quà theo loại người dùng")
  protected Long segmentRate;

  @NotNull(message = "Vị trí không được để trống")
  @Min(value = 0, message = "Vị trí phải lớn hơn hoặc bằng 0")
  @Schema(description = "Vị trí")
  protected Long position;
}
