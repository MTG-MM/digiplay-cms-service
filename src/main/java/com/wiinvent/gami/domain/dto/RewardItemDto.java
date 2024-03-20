package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardItemDto {

  @NotNull(message = "Tên phần thưởng không được để trống")
  @NotBlank(message = "Tên phần thưởng không được để trống")
  @Schema(description = "Tên phần thưởng")
  private String rewardName;

  @Schema(description = "Mô tả")
  private String description;

  @Schema(description = "URL hình ảnh")
  private String imageUrl;

  @NotNull(message = "Loại phần thưởng không được để trống")
  @Schema(description = "Loại phần thưởng")
  private RewardItemType rewardItemType;

  @NotNull(message = "Trạng thái không được để trống")
  @Schema(description = "Trạng thái")
  private Status status;

  @NotNull(message = "Có giới hạn không được để trống")
  @Schema(description = "Có giới hạn")
  private Boolean isLimited;

  @Schema(description = "ID bên ngoài")
  private String externalId;

  @Schema(description = "ID loại phần thưởng")
  private Long rewardTypeId;
}
