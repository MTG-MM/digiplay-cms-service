package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.reward.RewardItemInfo;
import lombok.Data;

import java.util.UUID;

@Data
public class GameTournamentUserResponse {
  private UUID userId;
  private String firstName;
  private Integer coin;
  private Double point;
  private RewardItemInfo rewardItemInfo;

  public GameTournamentUserResponse(UUID userId, String firstName, Double point, Integer coin, RewardItemInfo rewardItemInfo) {
    this.userId = userId;
    this.firstName = firstName;
    this.point = point;
    this.coin = coin;
    this.rewardItemInfo = rewardItemInfo;
  }
}
