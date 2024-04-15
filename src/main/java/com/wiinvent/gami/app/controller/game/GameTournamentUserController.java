package com.wiinvent.gami.app.controller.game;

import com.wiinvent.gami.domain.entities.type.GameTournamentType;
import com.wiinvent.gami.domain.response.GameTournamentEventResponse;
import com.wiinvent.gami.domain.response.GameTournamentUserResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.game.GameTournamentUserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/vt/cms/game-tournament/leaderboard")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class GameTournamentUserController {
  @Autowired
  GameTournamentUserService gameTournamentUserService;

  @GetMapping("event")
  public ResponseEntity<List<GameTournamentEventResponse>> getGameTournamentEvent(
      @RequestParam Integer gameTourId
  ) {
    return ResponseEntity.ok(gameTournamentUserService.getGameTournamentEvents(gameTourId));
  }

  @GetMapping("user")
  @PageableAsQueryParam
  public ResponseEntity<PageResponse<GameTournamentUserResponse>> getTournamentUserLeaderboard(
      @RequestParam(required = false) String eventId,
      @RequestParam(required = false) String name,
      @RequestParam GameTournamentType type,
      @RequestParam Integer gameTourId,
      @Parameter(hidden = true)
      Pageable pageable) {
    return ResponseEntity.ok(gameTournamentUserService.getUserLeaderboard(eventId, name, gameTourId, type, pageable));
  }
}
