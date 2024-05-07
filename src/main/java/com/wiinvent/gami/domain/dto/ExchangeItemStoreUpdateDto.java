package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class ExchangeItemStoreUpdateDto {
  private String name;
  private Integer coinPrice;
  private Integer pointPrice;
  private Integer ticketPrice;
  private Long startAt;
  private Long endAt;
  private Status status;
  private Long limitExchange;
}
