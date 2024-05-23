package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChallengeDetailUpdateDto {
  private String name;
  private String code;
  @NotNull
  private Integer coinPrice = 0;
  @NotNull
  private Integer pointPrice= 0;
  @NotNull
  private Integer level = 1;
  @NotNull
  private Status status = Status.ACTIVE;
  private List<UserRewardItems> rewardItems = new ArrayList<>();
  private List<UserRewardItems> rewardItemSpecial = new ArrayList<>();;
  private Integer duration = 0;
  private Boolean isSpecial = true;
}
