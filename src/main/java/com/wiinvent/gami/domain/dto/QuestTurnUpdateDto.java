package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.UserType;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestTurnUpdateDto {
  private String name;
  private Integer questNumber;

  @NotNull
  @Min(0)
  private Integer turn;
  @NotNull
  @Min(0)
  private Integer pointPrice;
  @NotNull
  @Min(0)
  private Integer coinPrice;
  @Min(0)
  private Integer questCd = 0;
  @Min(0)
  private Integer viewAdsSkipDuration = 0;
  @Min(0)
  @NotNull
  private Integer duration;
  @Min(0)
  private Integer durationBonus = 0;
  @Min(0)
  private Integer durationBonusPrice = 0;
  @NotNull
  private Status status;

  private List<UserType> activeForUser = new ArrayList<>();

  private List<UserType> freePointForUser = new ArrayList<>();

  private List<UserType> freeCoinForUser = new ArrayList<>();

  private List<UserRewardItems> rewardItems = new ArrayList<>();
}
