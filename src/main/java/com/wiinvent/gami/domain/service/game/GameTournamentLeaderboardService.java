package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.entities.game.GameTournamentEvent;
import com.wiinvent.gami.domain.response.GameTournamentEventResponse;
import com.wiinvent.gami.domain.response.GameTournamentUserResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class GameTournamentLeaderboardService extends BaseService {
  public List<GameTournamentEventResponse> getGameTournamentEvents(GameTournamentEvent gameTournamentEvent) {
    LocalDateTime localDateTimeNow = Helper.getNowDateTimeAtVn();
    List<GameTournamentEvent> gameTournamentEvents = gameTournamentEventStorage.getGameTournamentEvents(localDateTimeNow);
    return modelMapper.toGameTournamentEventResponse(gameTournamentEvents);
  }

}
