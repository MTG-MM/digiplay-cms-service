package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class RewardTypeResponse {

  private Long id;

  private String name;

  private String description;

  private String type;

  private Status status;

}
