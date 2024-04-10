package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.GameTournamentEvent;
import com.wiinvent.gami.domain.stores.BaseStorage;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class GameTournamentEventStorage extends BaseStorage {
  public List<GameTournamentEvent> getGameTournamentEvents(LocalDateTime startAt) {
    return gameTournamentEventRepository.findByStartTimeLessThanEqual(startAt);
  }

  public String getCurrentGameTournamentId() {
    LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    GameTournamentEvent gameTournamentEvent =
        gameTournamentEventRepository.findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Helper.convertFromVnToUtc(today), Helper.convertFromVnToUtc(today));
    if (gameTournamentEvent != null){
      return gameTournamentEvent.getId();
    }
    return null;
  }
}
