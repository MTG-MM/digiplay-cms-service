package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardItemUpdateDto {
  @NotNull(message = "Tên phần thưởng không được để trống")
  @NotBlank(message = "Tên phần thưởng không được để trống")
  @Schema(description = "Tên phần thưởng")
  private String rewardName;

  @Schema(description = "Mô tả")
  private String description;

  @Schema(description = "URL hình ảnh")
  private String imageUrl;

  @NotNull(message = "Trạng thái không được để trống")
  @Schema(description = "Trạng thái")
  private Status status;

  @Min(0)
  private Long value;

  @NotNull(message = "Có giới hạn không được để trống")
  @Schema(description = "Có giới hạn")
  private Boolean isLimited;
}
