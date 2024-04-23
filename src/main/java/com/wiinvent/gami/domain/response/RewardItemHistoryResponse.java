package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.pojo.RewardItemInfo;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.RewardType;
import lombok.Data;

import java.util.UUID;

@Data
public class RewardItemHistoryResponse {

  private UUID id;

  private String name;

  private RewardType rewardType;

  private Long rewardItemId; // id reward item

  private Long rewardSegmentId;

  private String note;

  private ResourceType resourceType;

  private RewardItemInfo rewardItemInfo;

  private Long createdAt;

}
