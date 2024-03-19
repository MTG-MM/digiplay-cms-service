package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.app.controller.base.BaseController;
import com.wiinvent.gami.app.controller.dto.GamePackageCreateDto;
import com.wiinvent.gami.app.controller.dto.GamePackageUpdateDto;
import com.wiinvent.gami.app.controller.response.GamePackageResponse;
import com.wiinvent.gami.app.controller.response.base.PageResponse;
import com.wiinvent.gami.domain.service.GamePackageService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
  public ResponseEntity<Boolean> createGamePackage(@PathVariable int id, @RequestBody GamePackageUpdateDto dto){
    gamePackageService.updateGamePackage(id, dto);
    return ResponseEntity.ok(true);
  }

}
