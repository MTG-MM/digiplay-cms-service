package com.wiinvent.gami.domain.dto;

import lombok.Data;

@Data
public class GameTournamentCreateDto extends GameTournamentUpdateDto{
  private Integer gameId;
}
