package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameTournamentType;
import lombok.Data;

@Data
public class GameTournamentCreateDto extends GameTournamentUpdateDto{
  private Integer gameId;
  private GameTournamentType type;
}
