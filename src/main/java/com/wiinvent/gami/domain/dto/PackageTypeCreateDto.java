package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class PackageTypeCreateDto {
  private String name;

  private ProductPackageType type;

  private Boolean isSkipAds;

  private Integer decreaseQuestCdPercent;

  private Integer bonusRate;

  private List<UserRewardItems> rewardItems;

  private Integer decreasePointPercent;

  private Integer expBonus;

  private Integer freeSpinDay;

  private Status status;
}
