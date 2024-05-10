package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.ExchangeStoreType;
import com.wiinvent.gami.domain.response.RewardItemSelect;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ExchangeItemStoreCreateDto extends ExchangeItemStoreUpdateDto {
  @Size(max = 1, min = 1)
  private List<RewardItemSelect> rewardItems;
  private ExchangeStoreType storeType;
}
