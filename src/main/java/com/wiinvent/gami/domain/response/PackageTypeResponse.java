package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class PackageTypeResponse {
  private Integer id;

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

  private Long createdAt;

  private Long updatedAt;
}