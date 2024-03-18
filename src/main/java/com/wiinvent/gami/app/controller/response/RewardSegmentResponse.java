package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RewardSegmentResponse {

  @Schema(description = "ID của đoạn phần thưởng")
  private Long id;

  @Schema(description = "Tên của đoạn phần thưởng")
  private String name;

  @Schema(description = "Mã của đoạn phần thưởng")
  private String code;

  @Schema(description = "URL hình ảnh của đoạn phần thưởng")
  private String imageUrl;

  @Schema(description = "Có tích luỹ ưu tiên hay không")
  private Boolean isAccumulativePriority;

  @Schema(description = "Trạng thái của đoạn phần thưởng")
  private Status status;
}
