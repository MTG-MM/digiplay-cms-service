package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.BannerType;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class BannerResponse {
  private Integer id;

  private BannerType type;

  private String imageUrl;

  private Status status;

  private String linkTo;

  private String linkTracking;
}
