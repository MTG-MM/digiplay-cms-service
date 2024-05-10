package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.PeriodLimitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RewardSegmentDetailResponse {

  @Schema(description = "ID của phần thưởng chi tiết")
  private Long id;

  @Schema(description = "Độ ưu tiên nhận quà theo tỉ lệ")
  private Long priority; // do ưu tiên nhận quà theo tỉ lệ

  @Schema(description = "Độ ưu tiên nhận quà theo loại người dùng")
  private Long segmentRate; // do ưu tiên nhận quà theo loại người dùng

  @Schema(description = "Vị trí")
  private Long position;

  @Schema(description = "ID của phần thưởng")
  private Long rewardItemId;

  @Schema(description = "ID của đoạn phần thưởng")
  private Long rewardSegmentId;

  @Schema(description = "Loại giới hạn khoảng thời gian")
  private PeriodLimitType periodType;

  @Schema(description = "Số khoảng thời gian")
  private Long periodNumber = 1L;

  @Schema(description = "Số quà tối đa người dùng có thể nhận trong khoảng thời gian")
  private Long periodValue = 1L;

  private Boolean isDefault = false;

  @Schema(description = "Số lượng trong danh sách bầu chọn")
  private Integer quantityInPoll = 0;

  @Schema(description = "Tên phần thưởng")
  private String rewardName;

  @Schema(description = "Có giới hạn hay không")
  private Boolean isLimited = false;
}
