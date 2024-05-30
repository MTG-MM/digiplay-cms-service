package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.DailyTaskType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskUpdateDto {
  private String name;
  private String description;
  @NotNull
  @Min(0)
  private Integer limitDay;
  @NotNull
  @Min(0)
  private Integer score;
  @NotNull
  private DailyTaskType type;
  @NotNull
  private Status status;
  private List<UserRewardItems> rewardItems = new ArrayList<>();
}
