package com.wiinvent.gami.domain.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameTournamentEventResponse {
  private String id;
  private Integer gameTournamentId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Boolean isCurrent;
}
