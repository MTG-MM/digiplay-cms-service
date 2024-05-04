package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RewardItemStoreResponse {

  @Schema(description = "ID của cửa hàng")
  private Long id;

  @Schema(description = "Tên của cửa hàng")
  private String name;

  private Long quantity;

  private Long usedQuantity;

  private Long totalQuantity;

  @Schema(description = "Loại cửa hàng")
  private StoreType type;

  @Schema(description = "Trạng thái của cửa hàng")
  private Status status;
}
