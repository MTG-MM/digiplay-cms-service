package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.PeriodType;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

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

  private Integer pointRequirement;

  private Integer coinRequirement;

  private Integer turnRequirement;

  private Integer totalRequirement;

  @Schema(description = "Có tích luỹ ưu tiên hay không")
  private Boolean isAccumulativePriority;

  @Schema(description = "Trạng thái của đoạn phần thưởng")
  private Status status;
  private ResourceType resourceType;
  private Long luckyPoint;
  private PeriodType periodType;

  @Getter(AccessLevel.NONE)
  private List<Integer> periodValue;

  public String getPeriodValue() {
    if (periodValue!= null) {
      return periodValue.stream()
          .map(String::valueOf)
          .collect(Collectors.joining(", "));
    } else {
      return null;
    }
  }
}
