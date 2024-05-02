package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ChallengeDetailUpdateDto {
  private String name;
  private String code;
  @NotNull
  private Integer coinPrice;
  @NotNull
  private Integer pointPrice;
  @NotNull
  private Integer level;
  @NotNull
  private Status status;
  private List<UserRewardItems> rewardItems;
  private List<UserRewardItems> rewardItemSpecial;
  private Integer duration;
  private Boolean isSpecial;
}
