package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.GameTournamentEvent;
import com.wiinvent.gami.domain.stores.BaseStorage;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class GameTournamentEventStorage extends BaseStorage {
  public List<GameTournamentEvent> getGameTournamentEvents(Integer gameId, LocalDateTime startAt) {
    return gameTournamentEventRepository.findByGameTournamentIdAndStartTimeLessThanEqual(gameId, startAt);
  }

  public String getCurrentGameTournamentId(Integer gameId) {
    LocalDateTime nowAtVn = DateUtils.getNowDateTimeAtVn();
    GameTournamentEvent gameTournamentEvent =
        gameTournamentEventRepository.findByGameTournamentIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(gameId, nowAtVn, nowAtVn);
    if (gameTournamentEvent != null){
      return gameTournamentEvent.getId();
    }
    return null;
  }
}
