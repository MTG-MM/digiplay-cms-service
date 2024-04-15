package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PackageCreateDto{
  @NotNull(message = "Không được để trống")
  private String code;

  private Integer point;

  private Integer coin;

  private Integer daySub;

  private Status status;

  private Integer coinDaily;

  private String externalImageId;

  @NotNull(message = "Không được để trống")
  private Integer packageTypeId;

  private Integer pointDaily;

  private String paymentMethodInfo;

  private Long startTime;

  private Long endTime;

  private Integer pointBonus;

  private Integer coinBonus;
}
