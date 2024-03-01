package com.managersystem.admin.handleRequest.controller.response;

import com.managersystem.admin.server.entities.type.RewardItemStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class VoucherDetailResponse {

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
}
