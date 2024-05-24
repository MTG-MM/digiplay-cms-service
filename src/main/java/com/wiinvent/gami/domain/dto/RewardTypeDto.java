package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RewardTypeDto {

  @NotBlank(message = "Tên không được để trống")
  @Schema(description = "Tên", example = "Loại quà A")
  protected String name;

  @Schema(description = "Mô tả")
  protected String description;

  @Schema(description = "Loại", example = "Type A")
  protected RewardItemType type;

  @Schema(description = "Trạng thái")
  protected Status status;
}
