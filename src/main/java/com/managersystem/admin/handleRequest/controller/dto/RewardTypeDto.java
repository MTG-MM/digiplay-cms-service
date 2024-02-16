package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.Status;
import lombok.Data;

@Data
public class RewardTypeDto {

  protected String name;

  protected String description;

  protected String type;

  protected Status status;
}
