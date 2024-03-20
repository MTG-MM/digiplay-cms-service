package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.PeriodType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RewardScheduleResponse {

  @Schema(description = "ID của lịch trình phần thưởng")
  private Long id;

  @Schema(description = "ID của phần thưởng chi tiết")
  private Long rewardSegmentDetailId;

  @Schema(description = "Loại khoảng thời gian")
  private PeriodType periodType;

  @Schema(description = "Có tích luỹ hay không")
  private Boolean isAccumulative;

  @Schema(description = "Số lượng")
  private Long quantity;

  @Schema(description = "Thời điểm bắt đầu")
  private Long startAt;

  @Schema(description = "Thời điểm kết thúc")
  private Long endAt;
}
