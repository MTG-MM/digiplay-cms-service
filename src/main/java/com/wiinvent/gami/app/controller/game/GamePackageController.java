package com.wiinvent.gami.app.controller.game;

import com.wiinvent.gami.app.controller.BaseController;
import com.wiinvent.gami.domain.dto.GamePackageCreateDto;
import com.wiinvent.gami.domain.dto.GamePackageUpdateDto;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.GamePackageResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.game.GamePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/game-package")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class GamePackageController extends BaseController {
  @Autowired private GamePackageService gamePackageService;

  @GetMapping("")
  public ResponseEntity<PageResponse<GamePackageResponse>> findAll(
      @RequestParam(value = "gameId") Integer gameId,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) Status status,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ){
    return ResponseEntity.ok(
        PageResponse.createFrom(gamePackageService.findAll(gameId, id, status, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<GamePackageResponse> getGamePackageDetail(
      @PathVariable(value = "id") int id
  ){
    return ResponseEntity.ok(gamePackageService.getPackageDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createGamePackage(@RequestBody GamePackageCreateDto gamePackageCreateDto){
    return ResponseEntity.ok(
        gamePackageService.createGamePackage(gamePackageCreateDto)
    );
  }

  @PutMapping("")
  public ResponseEntity<Boolean> updateGamePackage(@RequestBody GamePackageUpdateDto dto){
    return ResponseEntity.ok(
        gamePackageService.updateGamePackage(dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteGamePackage(@PathVariable int id){
    return ResponseEntity.ok(
        gamePackageService.deleteGamePackage(id)
    );
  }
}
