package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.PaymentMethodInfo;
import lombok.Data;

import java.util.List;

@Data
public class PackageResponse {
  private Integer id;

  private String code;

  private Integer point;

  private Integer coin;

  private Integer ticket;

  private Integer daySub;

  private Status status;

  private Integer coinDaily;

  private Integer packageTypeId;

  private Integer pointDaily;

  private List<PaymentMethodInfo> paymentMethodInfo;

  private Long startTime;

  private Long endTime;

  private Integer pointBonus;

  private Integer coinBonus;

  private Integer secondBonusPoint;

  private Integer secondBonusCoin;

  private Long createdAt;

  private Long updatedAt;

  private String name;

  private String description;

  private ProductPackageType type;

  private String imageUrl;
}
