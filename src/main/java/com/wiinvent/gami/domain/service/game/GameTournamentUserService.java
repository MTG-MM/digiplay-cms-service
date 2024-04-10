package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.entities.game.GameTournamentEvent;
import com.wiinvent.gami.domain.entities.type.GameTournamentType;
import com.wiinvent.gami.domain.response.GameTournamentEventResponse;
import com.wiinvent.gami.domain.response.GameTournamentUserResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class GameTournamentUserService extends BaseService {
  public List<GameTournamentEventResponse> getGameTournamentEvents(Integer gameTournamentId) {
    LocalDateTime localDateTimeNow = Helper.getNowDateTimeAtVn();
    List<GameTournamentEvent> gameTournamentEvents = gameTournamentEventStorage.getGameTournamentEvents(gameTournamentId, localDateTimeNow);
    return modelMapper.toGameTournamentEventResponse(gameTournamentEvents);
  }

  public PageResponse<GameTournamentUserResponse> getUserLeaderboard(String eventId, String name, Integer gameId, GameTournamentType type, Pageable pageable) {
    Page<GameTournamentUserResponse> gameTournamentUserResponses = null;
    if (eventId == null) {
      eventId = gameTournamentEventStorage.getCurrentGameTournamentId(gameId) == null ? null : gameTournamentEventStorage.getCurrentGameTournamentId(gameId);
    }
    switch (type) {
      case BEST_SCORE:
        gameTournamentUserResponses = gameTournamentUserStorage.getAllOrderByCoin(eventId, name, pageable);
        return PageResponse.createFrom(gameTournamentUserResponses);
      case BEST_POINT:
        gameTournamentUserResponses = gameTournamentUserStorage.getAllOrderByPoint(eventId, name, pageable);
        return PageResponse.createFrom(gameTournamentUserResponses);
      default:
        return PageResponse.createFrom(gameTournamentUserResponses);
    }
  }
}
