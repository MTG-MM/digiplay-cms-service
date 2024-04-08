package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class GameTournamentUpdateDto {
  private String name;
  private Long startAt;
  private Long endAt;
  private Long duration;
  private Status status;
}
