package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.DailyTaskType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class TaskResponse {
  private Integer id;
  private String name;
  private String description;
  private DailyTaskType type;
  private Integer limitDay;
  private Integer score;
  private Status status;
  private List<UserRewardItems> rewardItems;
}
