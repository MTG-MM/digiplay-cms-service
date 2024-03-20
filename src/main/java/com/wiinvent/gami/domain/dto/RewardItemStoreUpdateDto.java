package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RewardItemStoreUpdateDto {

  @NotNull(message = "Tên không được để trống")
  @NotBlank(message = "Tên không được để trống")
  @Schema(description = "Tên cửa hàng", example = "Cửa hàng A")
  private String name;

  @NotNull(message = "Loại cửa hàng không được để trống")
  @Schema(description = "Loại cửa hàng", example = "PHYSICAL_STORE")
  private StoreType type;

  @NotNull(message = "Trạng thái không được để trống")
  @Schema(description = "Trạng thái cửa hàng", example = "ACTIVE")
  private Status status;
}
