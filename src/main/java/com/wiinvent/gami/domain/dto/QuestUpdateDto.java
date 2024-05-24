package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestUpdateDto {
  private String name;
  private Integer gameId;
  private String description;
  private String imageUrl;
  @NotNull
  private String code;
  @NotNull
  private Status status;
  private List<UserRewardItems> rewardItems = new ArrayList<>();
}
