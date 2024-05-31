package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.QualifyRewardStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UserGoldPigResponse {
  private UUID id;
  private Integer price;
  private Long rewardAt;
  private QualifyRewardStatus status;
  private Integer accumulatePoint;
  private Long createdAt;
}
