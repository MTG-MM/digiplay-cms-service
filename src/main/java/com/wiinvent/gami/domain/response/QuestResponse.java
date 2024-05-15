package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class QuestResponse {
  private Long id;
  private String code;
  private String name;
  private Integer gameId;
  private String gameName;
  private String description;
  private String imageUrl;
  private Status status;
  private List<UserRewardItems> rewardItems;
}
