package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import lombok.Data;

import java.util.UUID;
@Data
public class InternalSubRequestDto {
  private UUID userId;
  private PaymentMethodType type;
  private String packageCode;
  private String transactionId;
  private Long startAt;
  private Long endAt;
}
