package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RewardItemResponse {

  @Schema(description = "ID của phần thưởng")
  private Long id;

  @Schema(description = "ID của loại phần thưởng")
  private Long rewardTypeId;

  @Schema(description = "Tên phần thưởng")
  private String rewardName;

  @Schema(description = "Mô tả của phần thưởng")
  private String description;

  @Schema(description = "URL hình ảnh của phần thưởng")
  private String imageUrl;

  @Schema(description = "Loại phần thưởng")
  private RewardItemType rewardItemType;

  @Schema(description = "Trạng thái của phần thưởng")
  private Status status;

  @Schema(description = "Có giới hạn hay không")
  private Boolean isLimited;

  @Schema(description = "Số lượng phần thưởng")
  private Long quantity;

  @Schema(description = "Tổng số lượng phần thưởng")
  private Long totalQuantity;

  @Schema(description = "Số lượng đã sử dụng")
  private Long usedQuantity;

  @Schema(description = "ID ngoài")
  private String externalId;

}
