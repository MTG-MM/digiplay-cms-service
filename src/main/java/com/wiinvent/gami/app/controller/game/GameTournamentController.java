package com.wiinvent.gami.app.controller.game;

import com.wiinvent.gami.domain.dto.GameTournamentCreateDto;
import com.wiinvent.gami.domain.dto.GameTournamentUpdateDto;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.GameTournamentResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.game.GameTournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("v1/portal/game-tournament")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class GameTournamentController {
  @Autowired
  GameTournamentService gameTournamentService;
  @GetMapping("{id}")
  public ResponseEntity<GameTournamentResponse> getGameTournamentDetail(@PathVariable String id){
    return ResponseEntity.ok(gameTournamentService.getGameTournamentDetail(id));
  }

  @GetMapping("")
  public ResponseEntity<PageResponse<GameTournamentResponse>> findAll(
      @RequestParam(required = false) Integer gameId,
      @RequestParam(required = false)Status status,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable){
    return ResponseEntity.ok(PageResponse.createFrom(gameTournamentService.findAll(gameId, status, pageable)));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createGameTournament(@RequestBody GameTournamentCreateDto dto){
    gameTournamentService.createGameTournament(dto);
    return ResponseEntity.ok(true);
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateGameTournament(@PathVariable String id, @RequestBody GameTournamentUpdateDto dto){
    gameTournamentService.updateGameTournament(id, dto);
    return ResponseEntity.ok(true);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deletePackage(@PathVariable String id){
    gameTournamentService.deleteGameTournament(id);
    return ResponseEntity.ok(true);
  }
}
