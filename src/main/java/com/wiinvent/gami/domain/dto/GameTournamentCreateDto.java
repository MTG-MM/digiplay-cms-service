package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameTournamentType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameTournamentCreateDto extends GameTournamentUpdateDto{
  @NotNull
  private GameTournamentType type;
}
