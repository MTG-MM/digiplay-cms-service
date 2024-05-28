package com.wiinvent.gami.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardItemDto extends RewardItemUpdateDto {
  @NotNull
  @Schema(description = "ID bên ngoài")
  private String externalId;

  @Schema(description = "ID loại phần thưởng")
  private Long rewardTypeId;
}
