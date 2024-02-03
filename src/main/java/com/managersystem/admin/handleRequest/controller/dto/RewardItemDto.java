package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import lombok.Data;

@Data
public class RewardItemDto {

  private String rewardName;

  private String description;

  private String imageUrl;

  private RewardType rewardType;

  private Status status;

  private Boolean isLimited;

  private String externalId;
}
