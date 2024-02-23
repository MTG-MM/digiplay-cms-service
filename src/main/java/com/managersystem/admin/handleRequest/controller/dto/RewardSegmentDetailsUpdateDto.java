package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.PeriodLimitType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardSegmentDetailsUpdateDto {
  @NotNull
  protected Long rewardItemId;
  
  protected Long priority = 0L; //do uu tien nhan qua theo ti le

  protected Long segmentRate = 0L; //do uu tien nhan qua theo loai nguoi dung

  protected Long position;

  private Boolean isDefault = false; //Là quà mặc định sẽ nhả ra nếu không còn quà

  private PeriodLimitType periodType = PeriodLimitType.DAY; //Khoảng thời gian

  private Long periodNumber = 1L; //Số khoảng thời gian (periodNumber = 3, periodType = DAY => 3 ngay nhan duoc toi da {periodValue} qua)

  private Long periodValue = 1L; //Số quà tối đa người dùng có thể nhận trong khoảng thời gian
}
