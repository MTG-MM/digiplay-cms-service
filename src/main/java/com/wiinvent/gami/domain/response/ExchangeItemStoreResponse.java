package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import lombok.Data;

@Data
public class ExchangeItemStoreResponse {
  private Long id;
  private String name;
  private Integer coinPrice;
  private Integer pointPrice;
  private Integer ticketPrice;
  private Long quantity;
  private ExchangeItemStoreResponse storeType;
  private Long rewardItemId;
  private String rewardItemName;
  private Status status;
  private Long limitExchange;
  private Long createdAt;
}
