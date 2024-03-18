package com.wiinvent.gami.app.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardSegmentDetailUpdateDto {

  @NotNull
  @Min(0)
  protected Long priority; //do uu tien nhan qua theo ti le

  @NotNull
  @Min(0)
  protected Long segmentRate; //do uu tien nhan qua theo loai nguoi dung

  @NotNull
  @Min(0)
  protected Long position;
}
