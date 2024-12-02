package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class LeaderboardResponse {
  private Long id;
  private String name;
  private String code;
  private Status status;
  private List<UserRewardItems> rewardItems;
  private Long createdAt;
}
