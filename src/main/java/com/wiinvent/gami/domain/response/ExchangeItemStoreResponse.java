package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.ExchangeStoreType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
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
  private List<UserRewardItems> rewardItems;
  private String rewardItemName;
  private Status status;
  private Long startAt;
  private Long endAt;
  private Long limitExchange;
  private Long createdAt;
}
