package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.CharacterCreateDto;
import com.wiinvent.gami.domain.dto.CharacterUpdateDto;
import com.wiinvent.gami.domain.entities.type.CharacterCategoryType;
import com.wiinvent.gami.domain.entities.type.CharacterType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.CharacterResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/portal/character")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@Tag(name = "Character", description = "Api character")
public class CharacterController {
  @Autowired
  CharacterService characterService;

  @GetMapping("")
  public ResponseEntity<PageResponse<CharacterResponse>> findAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) CharacterCategoryType categoryType,
      @RequestParam(required = false) Status status,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(
        PageResponse.createFrom(characterService.findAll(name, categoryType, status, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<CharacterResponse> getCharacterDetail(@PathVariable int id) {
    return ResponseEntity.ok(characterService.getCharacterDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createCharacter(@RequestBody CharacterCreateDto characterCreateDto) {
    return ResponseEntity.ok(
        characterService.createCharacter(characterCreateDto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateCharacter(@PathVariable Integer id, @RequestBody CharacterUpdateDto dto) {
    return ResponseEntity.ok(
        characterService.updateCharacter(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteCharacter(@PathVariable Integer id) {
    return ResponseEntity.ok(
        characterService.deleteCharacter(id)
    );
  }

  @GetMapping("active")
  public ResponseEntity<List<CharacterResponse>> getActiveCharacters(
      @RequestParam(required = false) CharacterCategoryType type
  ) {
    return ResponseEntity.ok(characterService.getCharactersActive(type));
  }

  @GetMapping("/category-type")
  @Operation(summary = "Lấy danh sách character category", description = "ví dụ: SKIN, OUTSIDE, PET, DECOR, ...")
  public ResponseEntity<List<CharacterCategoryType>> findAllCharacterType() {
    return ResponseEntity.ok(
        characterService.findAllCharacterType()
    );
  }

  @GetMapping("/category-type/{characterCategoryType}")
  @Operation(summary = "Lấy danh sách character type của character-category", description = "ví dụ: SKIN(TROUSER, SHIRT, ...), OUTSIDE(BACKGROUND, EFFECT, ...) ")
  public ResponseEntity<List<CharacterType>> findAllCharacterTypeByCategory(@PathVariable CharacterCategoryType characterCategoryType) {
    return ResponseEntity.ok(
        characterService.getCharacterTypeByCategory(characterCategoryType)
    );
  }
}
