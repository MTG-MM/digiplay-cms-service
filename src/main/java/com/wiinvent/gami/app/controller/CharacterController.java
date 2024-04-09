package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.CharacterCreateDto;
import com.wiinvent.gami.domain.dto.CharacterUpdateDto;
import com.wiinvent.gami.domain.dto.PackageCreateDto;
import com.wiinvent.gami.domain.dto.PackageUpdateDto;
import com.wiinvent.gami.domain.entities.type.CharacterCategoryType;
import com.wiinvent.gami.domain.entities.type.PackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.CharacterResponse;
import com.wiinvent.gami.domain.response.PackageResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/character")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
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
  ){
    return ResponseEntity.ok(
        PageResponse.createFrom(characterService.findAll(name, categoryType, status, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<CharacterResponse> getPackageDetail(@PathVariable int id){
    return ResponseEntity.ok(characterService.getCharacterDetail(id));
  }
  @PostMapping("")
  public ResponseEntity<Boolean> createCharacter(@RequestBody CharacterCreateDto characterCreateDto){
    return ResponseEntity.ok(
        characterService.createCharacter(characterCreateDto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateCharacter(@PathVariable Integer id, @RequestBody CharacterUpdateDto dto){
    return ResponseEntity.ok(
        characterService.updateCharacter(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deletePackage(@PathVariable Integer id){
    return ResponseEntity.ok(
        characterService.deleteCharacter(id)
    );
  }
}
