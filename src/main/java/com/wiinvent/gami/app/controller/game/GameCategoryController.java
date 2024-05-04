package com.wiinvent.gami.app.controller.game;

import com.wiinvent.gami.app.controller.BaseController;
import com.wiinvent.gami.domain.dto.GameCategoryCreateDto;
import com.wiinvent.gami.domain.dto.GameCategoryUpdateDto;
import com.wiinvent.gami.domain.dto.GameCategoryUpdateStatusDto;
import com.wiinvent.gami.domain.response.GameCategoryResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.game.GameCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/portal/game-category")
@Tag(name = "Game caterory", description = "Api cho danh mục game(FREE, SUB, ...)")
public class GameCategoryController extends BaseController {
  @Autowired
  private GameCategoryService gameCategoryService;

  @GetMapping("")
  @Operation(summary = "Lấy danh sách")
  public ResponseEntity<PageResponse<GameCategoryResponse>> findAll(
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    return ResponseEntity.ok(
        PageResponse.createFrom(gameCategoryService.findAll(pageable))
    );
  }

  @GetMapping("{id}")
  @Operation(summary = "Lấy thông tin chi tiết")
  public ResponseEntity<GameCategoryResponse> findAll(@PathVariable Integer id) {
    return ResponseEntity.ok(
        gameCategoryService.getGameCategoryDetail(id)
    );
  }

  @PostMapping("")
  @Operation(summary = "Tạo")
  public ResponseEntity<Boolean> createGameCategory(@RequestBody @Valid GameCategoryCreateDto dto) {
    return ResponseEntity.ok(
        gameCategoryService.createGameCategory(dto)
    );
  }

  @PutMapping("")
  @Operation(summary = "Câp nhật thông tin")
  public ResponseEntity<Boolean> updateGameCategory(@RequestBody @Valid GameCategoryUpdateDto dto) {
    return ResponseEntity.ok(
        gameCategoryService.updateGameCategory(dto)
    );
  }

  @PutMapping("status")
  @Operation(summary = "Cập nhật trạng thái")
  public ResponseEntity<Boolean> updateStatus(@RequestBody @Valid GameCategoryUpdateStatusDto dto) {
    return ResponseEntity.ok(
        gameCategoryService.updateStatusGameCategory(dto)
    );
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Xóa")
  public ResponseEntity<Boolean> deleteGameCategory(@PathVariable Integer id) {
    return ResponseEntity.ok(
        gameCategoryService.deleteGameCategory(id)
    );
  }

  @GetMapping("/active")
  @Operation(summary = "Lấy danh sách category active")
  public ResponseEntity<List<GameCategoryResponse>> findAllGameCategoryActive() {
    return ResponseEntity.ok(
      gameCategoryService.findAllGameCategoryActive()
    );
  }
}