package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.PeriodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardScheduleUpdateDto {

  @Schema(description = "id")
  private Integer id;

  @NotNull(message = "Loại khoảng thời gian không được để trống")
  @Schema(description = "Loại khoảng thời gian")
  private PeriodType periodType;

  @NotNull(message = "Có tích luỹ không được để trống")
  @Schema(description = "Có tích luỹ hay không")
  private Boolean isAccumulative;

  @NotNull(message = "Số lượng không được để trống")
  @Schema(description = "Số lượng")
  private Long quantity;

  @NotNull(message = "Ngày bắt đầu không được để trống")
  @Schema(description = "Ngày bắt đầu")
  private Long startAt;

  @NotNull(message = "Ngày kết thúc không được để trống")
  @Schema(description = "Ngày kết thúc")
  private Long endAt;
}
