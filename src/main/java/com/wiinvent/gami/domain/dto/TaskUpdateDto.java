package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.DailyTaskType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskUpdateDto {
  private String name;
  private String description;
  @NotNull
  private Integer limitDay;
  @NotNull
  private Integer score;
  @NotNull
  private DailyTaskType type;
  @NotNull
  private Status status;
  private List<UserRewardItems> rewardItems = new ArrayList<>();
}
