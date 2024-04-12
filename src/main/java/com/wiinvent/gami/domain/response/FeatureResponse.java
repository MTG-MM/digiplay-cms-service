package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.FeatureCode;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class FeatureResponse {
  private Integer id;

  private String name;

  private Status status;

  private Integer levelUnlock;

  private FeatureCode code;

  private Long createdAt;

  private Long updatedAt;
}