package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.GameTournamentType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserSegmentRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class GameTournamentResponse {
  private Integer gameId;
  private String name;
  private Long startAt;
  private Long endAt;
  private Integer duration;
  private Status status;
  private GameTournamentType type;
  private List<UserSegmentRewardItems> rewardItems;
  private Long createdAt;

}
