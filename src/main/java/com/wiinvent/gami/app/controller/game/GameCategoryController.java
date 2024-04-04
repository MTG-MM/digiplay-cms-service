package com.wiinvent.gami.app.controller.game;

import com.wiinvent.gami.app.controller.BaseController;
import com.wiinvent.gami.domain.dto.GameCategoryCreateDto;
import com.wiinvent.gami.domain.dto.GameCategoryUpdateDto;
import com.wiinvent.gami.domain.response.GameCategoryResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.game.GameCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vt/cms/game-category")
public class GameCategoryController extends BaseController {
  @Autowired private GameCategoryService gameCategoryService;

  @GetMapping("")
  public ResponseEntity<PageResponse<GameCategoryResponse>> findAll(Pageable pageable){
    return ResponseEntity.ok(
      PageResponse.createFrom(gameCategoryService.findAll(pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<GameCategoryResponse> findAll(@PathVariable Integer id){
    return ResponseEntity.ok(
        gameCategoryService.getGameCategoryDetail(id)
    );
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createGameCategory(@RequestBody @Valid GameCategoryCreateDto dto){
    return ResponseEntity.ok(
      gameCategoryService.createGamePackage(dto)
    );
  }

  @PutMapping("")
  public ResponseEntity<Boolean> updateGameCategory(@RequestBody @Valid GameCategoryUpdateDto dto){
    return ResponseEntity.ok(
        gameCategoryService.updateGameCategory(dto)
    );
  }
}