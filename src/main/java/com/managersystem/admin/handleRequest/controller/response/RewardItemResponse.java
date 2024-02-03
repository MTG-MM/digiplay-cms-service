package com.managersystem.admin.handleRequest.controller.response;

import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RewardItemResponse {

  private Long id;

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
