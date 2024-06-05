package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.RewardStateType;
import com.wiinvent.gami.domain.pojo.RewardItemInfo;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import lombok.Data;

import java.util.UUID;

@Data
public class RewardItemHistoryResponse {

  private UUID id;

  private String name;

  private RewardItemType rewardItemType;

  private Long rewardItemId; // id reward item

  private Long rewardSegmentId;

  private String note;

  private ResourceType resourceType;

  private RewardItemInfo rewardItemInfo;

  private RewardStateType rewardState;

  private Long createdAt;

}
