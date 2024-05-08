package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExchangeItemStoreUpdateDto {
  private String name;
  @NotNull
  private Integer coinPrice;
  @NotNull
  private Integer pointPrice;
  @NotNull
  private Integer ticketPrice;
  private Long startAt;
  private Long endAt;
  @NotNull
  private Status status;
  private Long limitExchange;
}
