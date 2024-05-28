package com.wiinvent.gami.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.entities.type.PeriodType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class RewardScheduleUpdateDto {

  @Schema(description = "id")
  private Long id;

  @NotNull(message = "Loại khoảng thời gian không được để trống")
  @Schema(description = "Loại khoảng thời gian")
  private PeriodType periodType;

  @NotNull(message = "Có tích luỹ không được để trống")
  @Schema(description = "Có tích luỹ hay không")
  private Boolean isAccumulative;

  @NotNull(message = "Số lượng không được để trống")
  @Schema(description = "Số lượng")
  private Long quantity;

  @NotNull(message = "Trạng thái không được để trống")
  @Schema(description = "Trạng thái")
  private Status status;

  @Getter(AccessLevel.NONE)
  @NotNull(message = "Ngày bắt đầu không được để trống")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "Ngày bắt đầu", example = "2021-10-10 10:10:10")
  private LocalDateTime startAt;

  @Getter(AccessLevel.NONE)
  @NotNull(message = "Ngày kết thúc không được để trống")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "Ngày kết thuc", example = "2021-10-10 10:10:10")
  private LocalDateTime endAt;

  public Long getEndAt() {
    return DateUtils.timeToLongAtUtc(endAt);
  }

  public Long getStartAt() {
    return DateUtils.timeToLongAtUtc(startAt);
  }
}
