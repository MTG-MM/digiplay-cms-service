package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PackageTypeCreateDto {
  private String name;

  @NotNull(message = "Không được để trống")
  private ProductPackageType type;

  private Boolean isSkipAds;
  @Min(0)
  private Integer decreaseQuestCdPercent;

  @Min(0)
  private Integer bonusRate;

  private List<UserRewardItems> rewardItems = new ArrayList<>();
  @Min(0)
  private Integer decreasePointPercent;
  @Min(0)
  private Integer expBonus;
  @Min(0)
  private Integer freeSpinDay;

  @NotNull(message = "Không được để trống")
  private Status status;
}
