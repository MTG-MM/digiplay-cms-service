package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PackageTypeCreateDto {
  private String name;

  @NotNull(message = "Không được để trống")
  private ProductPackageType type;

  private Boolean isSkipAds;

  private Integer decreaseQuestCdPercent;

  private Integer bonusRate;

  private List<UserRewardItems> rewardItems;

  private Integer decreasePointPercent;

  private Integer expBonus;

  private Integer freeSpinDay;

  @NotNull(message = "Không được để trống")
  private Status status;
}
