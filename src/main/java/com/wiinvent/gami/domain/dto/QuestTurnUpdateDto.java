package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.UserType;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestTurnUpdateDto {
  private String name;
  private Integer questNumber;

  @NotNull
  private Integer turn;
  @NotNull
  private Integer pointPrice;
  @NotNull
  private Integer coinPrice;

  private Integer questCd = 0;

  private Integer viewAdsSkipDuration = 0;

  private Integer coinSkipCdPrice = 0;

  @NotNull
  private Integer duration;

  private Integer durationBonus = 0;

  private Integer durationBonusPrice = 0;
  @NotNull
  private Status status;

  private List<UserType> activeForUser = new ArrayList<>();

  private List<UserType> freeForUser = new ArrayList<>();

  private List<UserRewardItems> rewardItems = new ArrayList<>();
}
