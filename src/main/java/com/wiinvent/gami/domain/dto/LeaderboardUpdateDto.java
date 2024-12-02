package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class LeaderboardUpdateDto {
  private String name;
  @NotNull
  private Status status;
  private List<UserRewardItems> rewardItems;
}
