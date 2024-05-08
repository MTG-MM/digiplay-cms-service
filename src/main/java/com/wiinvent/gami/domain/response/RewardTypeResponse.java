package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.utils.DateUtils;
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
  private RewardItemType type;

  @Schema(description = "Trạng thái của loại phần thưởng")
  private Status status;

  private Long createdAt;
}
