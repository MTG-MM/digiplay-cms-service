package com.wiinvent.gami.app.controller.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class RewardSegmentDto {

  private String name;

  private String code;

  private String imageUrl;

  private Boolean isAccumulativePriority;

  private Status status;
}
