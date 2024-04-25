package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.pojo.AchievementInfo;
import lombok.Data;

import java.util.UUID;

@Data
public class AchievementUserResponse {
  private UUID id;
  private Integer achievementId;
  private String name;
  private AchievementInfo achievementInfo;
  private Double score;
  private Long createdAt;
}
