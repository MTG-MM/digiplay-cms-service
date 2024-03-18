package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class RewardSegmentResponse {

  private Long id;

  private String name;

  private String code;

  private String imageUrl;

  private Boolean isAccumulativePriority;

  private Status status;
}
