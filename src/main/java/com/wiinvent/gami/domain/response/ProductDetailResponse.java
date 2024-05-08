package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDetailResponse {
  private UUID id;

  private Long storeId;

  private String name;

  private String code;

  private UUID userId;

  private Long givenAt;

  private RewardItemStatus status;

  private Long segmentDetailId;

  private Long startAt;

  private Long expireAt;

  private Long createdAt;

}
