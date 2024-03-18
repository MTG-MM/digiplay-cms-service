package com.wiinvent.gami.app.controller.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class RewardTypeDto {

  protected String name;

  protected String description;

  protected String type;

  protected Status status;
}
