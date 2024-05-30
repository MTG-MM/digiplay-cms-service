package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.PeriodType;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class RewardSegmentDto {

  @NotBlank(message = "Tên không được để trống")
  @Schema(description = "Tên", example = "Segment A")
  private String name;

  @NotBlank(message = "Mã không được để trống")
  @Schema(description = "Mã", example = "SEGMENT_A")
  private String code;

  @Schema(description = "URL hình ảnh")
  private String imageUrl;

  @Min(0)
  private Integer pointRequirement = 0;
  private Integer ticketRequirement;


  @Min(0)
  private Integer coinRequirement = 0;

  @Min(0)
  private Integer turnRequirement = 0;

  @Schema(description = "Có tích luỹ ưu tiên hay không")
  private Boolean isAccumulativePriority;

  @Schema(description = "Trạng thái")
  private Status status;

  private ResourceType resourceType;

  @Min(0)
  private Long luckyPoint = 0L;
  private PeriodType periodType;
  @Setter(AccessLevel.NONE)
  private List<Integer> periodValue = new ArrayList<>();

  public void setPeriodValue(String periodValue) {
    if (periodValue != null) {
      this.periodValue = Arrays.stream(periodValue.split(","))
          .map(String::trim)
          .map(Integer::parseInt)
          .toList();
    } else {
      this.periodValue = null;
    }
  }
}
