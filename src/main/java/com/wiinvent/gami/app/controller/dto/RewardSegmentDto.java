package com.wiinvent.gami.app.controller.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RewardSegmentDto {

  @NotBlank(message = "Tên không được để trống")
  @Schema(description = "Tên", example = "Segment A")
  private String name;

  @NotBlank(message = "Mã không được để trống")
  @Schema(description = "Mã", example = "SEGMENT_A")
  private String code;

  @Schema(description = "URL hình ảnh")
  private String imageUrl;

  @Schema(description = "Có tích luỹ ưu tiên hay không")
  private Boolean isAccumulativePriority;

  @Schema(description = "Trạng thái")
  private Status status;
}
