package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.UserType;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class QuestTurnResponse {
  private Long id;

  private String name;

  private Integer questNumber;

  private Integer turn;

  private Integer pointPrice;

  private Integer coinPrice;

  private Integer questCd;

  private Integer viewAdsSkipDuration;

  private Integer duration;

  private Integer durationBonus;

  private Integer durationBonusPrice;

  private Status status;

  private List<UserType> activeForUser;

  private List<UserType> freePointForUser;

  private List<UserType> freeCoinForUser;

  private List<UserRewardItems> rewardItems;
}
