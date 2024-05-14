package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.DailyTaskType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class TaskUpdateDto {
  private String name;
  private String description;
  private Integer limitDay;
  private Integer score;
  private DailyTaskType type;
  private Status status;
  private List<UserRewardItems> rewardItems;
}
