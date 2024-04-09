package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.user.UserSegmentRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class UserSegmentUpdateDto {
  private String name;
  private Boolean isDefault;
  private Long minPriority;
  private Long maxPriority;
  private Integer level;
  private List<UserSegmentRewardItems> rewardItems;
  private Integer requireExp;
  private Integer pointLimit;
  private Integer pointBonusRate;
  private Integer subBonusRate;
}
