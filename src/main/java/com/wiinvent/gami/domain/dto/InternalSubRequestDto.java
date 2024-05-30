package com.wiinvent.gami.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
public class InternalSubRequestDto {
  private UUID userId;
  private PaymentMethodType type;
  private String packageCode;
  private String transactionId;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate startAt;
}
