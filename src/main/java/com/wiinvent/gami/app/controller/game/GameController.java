package com.wiinvent.gami.app.controller.game;

import com.wiinvent.gami.app.controller.BaseController;
import com.wiinvent.gami.domain.dto.GameCreateDto;
import com.wiinvent.gami.domain.dto.GameTypeCreateDto;
import com.wiinvent.gami.domain.dto.GameTypeUpdateDto;
import com.wiinvent.gami.domain.dto.GameUpdateDto;
import com.wiinvent.gami.domain.entities.type.GameStatus;
import com.wiinvent.gami.domain.response.GameResponse;
import com.wiinvent.gami.domain.response.GameTypeResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.game.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/portal/game")
@Tag(name = "Game", description = "Api cho danh game")
public class GameController extends BaseController {

  @Autowired private GameService gameService;
  @GetMapping("")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
  @PageableAsQueryParam
  @Operation(summary = "Lấy danh sách game")
  public PageResponse<GameResponse> getAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) GameStatus status,
      @RequestParam(required = false) Boolean isHot,
      @RequestParam(required = false) Boolean isNew,
      @RequestParam(required = false) Boolean isUpdate,
      @RequestParam(required = false) Boolean isLock,
      @RequestParam(required = false) Integer gameCategoryId,
      @RequestParam(required = false) Integer gameTypeId,
      @Parameter(hidden = true)
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    return PageResponse.createFrom(gameService.getAll(id, name, status, isHot, isNew, isUpdate, isLock, gameCategoryId, gameTypeId, pageable));
  }

  @GetMapping("{id}")
  @Operation(summary = "Lấy thông tin chi tiết game")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('PUBLISHER')")
  public ResponseEntity<GameResponse> getGameDetail(@PathVariable Integer id) {
    return ResponseEntity.ok(gameService.getGameDetail(id));
  }

  @PostMapping("")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
  @Operation(summary = "Tạo game")
  public ResponseEntity<Boolean> createGame(@RequestBody @Valid GameCreateDto createDto) {
    return ResponseEntity.ok(
        gameService.createGames(createDto)
    );
  }

  @PutMapping("{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('PUBLISHER')")
  @Operation(summary = "Sửa thông tin game")
  public ResponseEntity<Boolean> updateGames(@PathVariable Integer id, @RequestBody @Valid GameUpdateDto updateDto) {
    return ResponseEntity.ok(
        gameService.updateGame(id, updateDto)
    );
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Xóa game")
  public ResponseEntity<Boolean> deleteGame(@PathVariable Integer id){
    return ResponseEntity.ok(
        gameService.deleteGame(id)
    );
  }

  @GetMapping("/status")
  public ResponseEntity<List<GameStatus>> findAllGameStatus(){
    return ResponseEntity.ok(
        gameService.findAllGameStatus()
    );
  }

  //======================================================= GAME TYPE ===================================================
  @GetMapping("/type")
  @Operation(summary = "Danh sách các loại game")
  @PageableAsQueryParam
  public ResponseEntity<PageResponse<GameTypeResponse>> getGameTypes(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer id,
      @Parameter(hidden = true) Pageable pageable) {
    return ResponseEntity.ok(
        PageResponse.createFrom(gameService.findGameTypes(id, name, pageable))
    );
  }

  @GetMapping("/type/{id}")
  @Operation(summary = "Lấy thông tin chi tiết loại game")
  public ResponseEntity<GameTypeResponse> getGameTypeDetail(@PathVariable Integer id) {
    return ResponseEntity.ok(gameService.getGameTypeDetail(id));
  }


  @PostMapping("/type")
  @Operation(summary = "Tạo loại game")
  public ResponseEntity<Boolean> createGameType(@RequestBody @Valid GameTypeCreateDto dto){
    return ResponseEntity.ok(gameService.createGameType(dto));
  }

  @PutMapping("/type")
  @Operation(summary = "Cập nhật thông tin loại game")
  public ResponseEntity<Boolean> updateGameType(@RequestBody @Valid GameTypeUpdateDto dto){
    return ResponseEntity.ok(gameService.updateGameType(dto));
  }

  @DeleteMapping("/type/{id}")
  @Operation(summary = "Xóa loại game")
  public ResponseEntity<Boolean> deleteGameType(@PathVariable Integer id){
    return ResponseEntity.ok(gameService.deleteGameType(id));
  }

  @GetMapping("/type/active")
  @Operation(summary = "Các thể loại game đang active")
  public ResponseEntity<List<GameTypeResponse>> findAllGameTypeActive(){
    return ResponseEntity.ok(
        gameService.findAllGameTypeActive()
    );
  }
}
