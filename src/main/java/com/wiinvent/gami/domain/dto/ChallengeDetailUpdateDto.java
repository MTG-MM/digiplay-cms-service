package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChallengeDetailUpdateDto {
  private String name;
  private String code;
  @NotNull
  @Min(0)
  private Integer coinPrice;
  @NotNull
  @Min(0)
  private Integer pointPrice;
  @NotNull
  @Min(1)
  private Integer level = 1;
  @NotNull
  private Status status;
  private List<UserRewardItems> rewardItems = new ArrayList<>();
  private List<UserRewardItems> rewardItemSpecial = new ArrayList<>();
  @NotNull
  @Min(0)
  private Integer duration = 0;
  private Boolean isSpecial = true;
}
