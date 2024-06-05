package com.wiinvent.gami.domain.pojo;

import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

import java.util.List;

@Data
public class PackageInfo {
  private Integer id;
  private String code;
  private Integer point;
  private Integer coin;
  private Integer ticket;
  private Integer dayDuration;
  private Status status;
  private Integer coinDaily;
  private String imageUrl;
  private Integer packageTypeId;
  private ProductPackageType type;
  private Integer pointDaily;
  private List<PaymentMethodInfo> paymentMethodInfo;
  private Long startTime;
  private Long endTime;
  private Integer pointBonus;
  private Integer coinBonus;
  private String name;
  private String description;
  private Integer secondBonusPoint;
  private Integer secondBonusCoin;
  private Long createdAt;
  private Long updatedAt;
}
