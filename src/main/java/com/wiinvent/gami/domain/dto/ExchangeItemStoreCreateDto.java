package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.ExchangeStoreType;
import lombok.Data;

@Data
public class ExchangeItemStoreCreateDto extends ExchangeItemStoreUpdateDto {
  private Long rewardItemId;
  private ExchangeStoreType storeType;
}
