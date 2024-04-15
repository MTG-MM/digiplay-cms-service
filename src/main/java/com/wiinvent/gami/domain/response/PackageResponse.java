package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class PackageResponse {
  private Integer id;

  private String code;

  private Integer point;

  private Integer coin;

  private Integer daySub;

  private Status status;

  private Integer coinDaily;

  private String externalImageId;

  private Integer packageTypeId;

  private Integer pointDaily;

  private String paymentMethodInfo;

  private Long startTime;

  private Long endTime;

  private Integer pointBonus;

  private Integer coinBonus;

  private Long createdAt;

  private Long updatedAt;
}
