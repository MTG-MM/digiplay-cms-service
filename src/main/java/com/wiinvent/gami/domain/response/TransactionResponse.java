package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.TransactionType;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionResponse {
  private UUID id;
  private Long amount;
  private TransactionType type;
  private String note;
  private String resourceType;
  private Long balance;
  private Long createdAt;
}
