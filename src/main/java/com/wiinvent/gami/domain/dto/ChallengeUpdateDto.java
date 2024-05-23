package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.RewardItemSelect;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ChallengeUpdateDto {
  private String name;
  private Integer gameId;
  private String imageUrl;
  private String thumbUrl;
  @NotNull
  private Status status;
  private List<RewardItemSelect> rewardItems;
}
