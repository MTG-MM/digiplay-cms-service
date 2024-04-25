package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.AchievementType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.AchievementInfo;
import lombok.Data;

import java.util.List;

@Data
public class AchievementResponse {
  private Integer id;
  private String name;
  private String description;
  private Status status;
  private AchievementType type;
  private List<AchievementInfo> achievementInfo;
}
