package com.wiinvent.gami.app.controller.game;

import com.wiinvent.gami.app.controller.BaseController;
import com.wiinvent.gami.domain.dto.GamePackageCreateDto;
import com.wiinvent.gami.domain.dto.GamePackageUpdateDto;
import com.wiinvent.gami.domain.response.GamePackageResponse;
import com.wiinvent.gami.domain.service.GamePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/game-package")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class GamePackageController extends BaseController {

  @Autowired private GamePackageService gamePackageService;

  @GetMapping("{id}")
  public ResponseEntity<GamePackageResponse> getGamePackageDetail(@PathVariable int id){
    return ResponseEntity.ok(gamePackageService.getPackageDetail(id));
  }
  @PostMapping("")
  public ResponseEntity<Boolean> createGamePackage(@RequestBody GamePackageCreateDto gamePackageCreateDto){
    gamePackageService.createGamePackage(gamePackageCreateDto);
    return ResponseEntity.ok(true);
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateGamePackage(@PathVariable int id, @RequestBody GamePackageUpdateDto dto){
    gamePackageService.updateGamePackage(id, dto);
    return ResponseEntity.ok(true);
  }

}
