package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameTournamentType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.user.UserSegmentRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class GameTournamentUpdateDto {
  private String name;
  private Long startAt;
  private Long endAt;
  private Long duration;
  private List<UserSegmentRewardItems> rewardItems;
  private Status status;
}
