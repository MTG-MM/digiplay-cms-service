package com.wiinvent.gami.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardItemDto extends RewardItemUpdateDto {
  @Schema(description = "ID bên ngoài")
  private String externalId;

  @Schema(description = "ID loại phần thưởng")
  private Long rewardTypeId;

  private Long quantity;
}
