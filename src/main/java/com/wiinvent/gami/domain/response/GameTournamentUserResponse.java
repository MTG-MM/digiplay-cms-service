package com.wiinvent.gami.domain.response;

import lombok.Data;

import java.util.UUID;

@Data
public class GameTournamentUserResponse {
  private UUID id;
  private String tournamentEventId;
  private Integer coin;
  private Double point;
  private Long createdAt;
}
