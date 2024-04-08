package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class GameTournamentResponse {
  private Integer gameId;
  private String name;
  private Long startAt;
  private Long endAt;
  private Integer duration;
  private Status status;
  private Long createdAt;
  private Long updatedAt;
}
