package com.wiinvent.gami.domain.pojo;

import com.wiinvent.gami.domain.entities.type.ProductType;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class PackageInfo {
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
  private String name;
  private String description;
  private ProductType type;
}
