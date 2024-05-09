package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class QuestResponse {
  private Long id;
  private String code;
  private String description;
  private String imageUrl;
  private Integer gameId;
  private List<UserRewardItems> rewardItems;
}
