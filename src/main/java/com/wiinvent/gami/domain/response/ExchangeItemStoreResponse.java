package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.ExchangeStoreType;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

import java.util.List;

@Data
public class ExchangeItemStoreResponse {
  private Long id;
  private String name;
  private Integer coinPrice;
  private Integer pointPrice;
  private Integer ticketPrice;
  private Long quantity;
  private ExchangeStoreType storeType;
  private List<RewardItemSelect> rewardItems;
  private String rewardItemName;
  private Status status;
  private Long startAt;
  private Long endAt;
  private Long limitExchange;
  private Long createdAt;
}
