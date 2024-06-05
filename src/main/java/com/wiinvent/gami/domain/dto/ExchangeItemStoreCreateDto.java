package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.ExchangeStoreType;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import com.wiinvent.gami.domain.response.RewardItemSelect;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ExchangeItemStoreCreateDto extends ExchangeItemStoreUpdateDto {
  private ExchangeStoreType storeType;
}
