package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.app.controller.base.BaseController;
import com.wiinvent.gami.app.controller.dto.GameCreateDto;
import com.wiinvent.gami.app.controller.dto.GameUpdateDto;
import com.wiinvent.gami.app.controller.response.GameResponse;
import com.wiinvent.gami.app.controller.response.base.PageResponse;
import com.wiinvent.gami.domain.security.service.UserDetailsImpl;
import com.wiinvent.gami.domain.service.GameService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/game")
public class GameController extends BaseController {

  @Autowired private GameService gameService;
  @GetMapping("")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
  @PageableAsQueryParam
  public PageResponse<GameResponse> getAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer id,
      @Parameter(hidden = true) Pageable pageable) {
    return PageResponse.createFrom(gameService.getAll(id, name, pageable));
  }

  @GetMapping("{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('PUBLISHER')")
  public ResponseEntity<GameResponse> getGameDetail(@PathVariable Integer id) {
    return ResponseEntity.ok(gameService.getGameDetail(id));
  }

  @PostMapping("")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
  public ResponseEntity<Boolean> createGame(@RequestBody GameCreateDto createDto) {
    gameService.createGames(createDto);
    return ResponseEntity.ok(true);
  }

  @PutMapping("{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('PUBLISHER')")
  public ResponseEntity<Boolean> updateGames(@PathVariable Integer id, @RequestBody @Valid GameUpdateDto updateDto) {
    gameService.updateGame(id, updateDto);
    return ResponseEntity.ok(true);
  }
}
