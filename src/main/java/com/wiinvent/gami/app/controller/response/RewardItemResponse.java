package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.RewardType;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class RewardItemResponse {

  private Long id;
  private Long rewardTypeId;

  private String rewardName;

  private String description;

  private String imageUrl;

  private RewardType rewardType;

  private Status status;

  private Boolean isLimited;

  private Long quantity;

  private Long totalQuantity;

  private Long usedQuantity;

  private String externalId;

}
