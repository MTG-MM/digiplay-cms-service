package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.Status;
import lombok.Data;

@Data
public class RewardSegmentDto {

  private String name;

  private String code;

  private String imageUrl;

  private Boolean isAccumulativePriority;

  private Status status;
}
