package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class RwItemStoreDetailResponse {

  @Schema(description = "ID của voucher")
  private UUID id;

  @Schema(description = "ID của cửa hàng")
  private Long storeId;

  @Schema(description = "Tên của voucher")
  private String name;

  @Schema(description = "Mã của voucher")
  private String code;

  @Schema(description = "ID của người dùng")
  private UUID userId;

  @Schema(description = "Thời điểm cấp voucher")
  private Long givenAt;

  @Schema(description = "Trạng thái của voucher")
  private RewardItemStatus status;

  @Schema(description = "ID của chi tiết đoạn phần thưởng")
  private Long segmentDetailId;

  @Schema(description = "Thời điểm bắt đầu hiệu lực của voucher")
  private Long startAt;

  @Schema(description = "Thời điểm kết thúc hiệu lực của voucher")
  private Long expireAt;

  private Long createdAt;


}
