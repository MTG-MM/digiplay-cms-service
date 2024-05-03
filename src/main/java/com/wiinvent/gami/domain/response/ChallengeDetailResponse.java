package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class ChallengeDetailResponse {
  private Integer id;
  private Integer challengeId;
  private String name;
  private String code;
  private Integer coinPrice;
  private Integer pointPrice;
  private Integer level;
  private Status status;
  private List<UserRewardItems> rewardItems;
  private List<UserRewardItems> rewardItemSpecial;
  private Integer duration;
  private Boolean isSpecial;
}
