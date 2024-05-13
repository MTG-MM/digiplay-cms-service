package com.wiinvent.gami.domain.pojo;

import com.wiinvent.gami.domain.response.RewardItemSelect;
import lombok.Data;

import java.util.List;
@Data
public class AchievementInfo {
  private int level = 1;
  private int minScore = 0;
  private int maxScore = 0;
  private List<UserRewardItems> rewardItems;
}
