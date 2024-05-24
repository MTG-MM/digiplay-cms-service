package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.AchievementType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.AchievementInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AchievementUpdateDto {
  private String name;
  private String description;
  @NotNull
  private AchievementType type;
  private List<AchievementInfo> achievementInfo = new ArrayList<>();
  @NotNull
  private Status status;
}
