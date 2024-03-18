package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RewardTypeResponse {

  @Schema(description = "ID của loại phần thưởng")
  private Long id;

  @Schema(description = "Tên của loại phần thưởng")
  private String name;

  @Schema(description = "Mô tả của loại phần thưởng")
  private String description;

  @Schema(description = "Loại của loại phần thưởng")
  private String type;

  @Schema(description = "Trạng thái của loại phần thưởng")
  private Status status;

}
