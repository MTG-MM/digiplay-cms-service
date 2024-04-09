package com.wiinvent.gami.domain.service.user.transaction;
import com.wiinvent.gami.domain.entities.user.GameTournamentUser;
import com.wiinvent.gami.domain.response.GameTournamentUserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class GameTournamentUserService extends BaseService {
  public PageCursorResponse<GameTournamentUserResponse> getGameTournament(UUID userId, Long next, Long pre, int limit) {
    List<GameTournamentUser> gameTournamentUsers = gameTournamentUserStorage.findAll(userId, next, pre, limit);
    List<GameTournamentUserResponse> responses = modelMapper.toGameTournamentUserResponse(gameTournamentUsers);
    return new PageCursorResponse<>(responses, limit, next, pre, "created");
  }
}

