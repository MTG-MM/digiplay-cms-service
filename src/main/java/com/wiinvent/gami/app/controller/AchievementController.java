package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.AchievementCreateDto;
import com.wiinvent.gami.domain.dto.AchievementUpdateDto;
import com.wiinvent.gami.domain.entities.type.AchievementType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.AchievementResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.AchievementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v1/portal/achievement")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@Tag(name = "Achievement", description = "Api achievement")
public class AchievementController {
  @Autowired
  AchievementService achievementService;

  @GetMapping("")
  public ResponseEntity<PageResponse<AchievementResponse>> findAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) AchievementType achievementType,
      @RequestParam(required = false) Status status,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(
        PageResponse.createFrom(achievementService.getAchievements(name, status, achievementType, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<AchievementResponse> getAchievementDetail(@PathVariable int id) {
    return ResponseEntity.ok(achievementService.getAchievementDetail(id));
  }

  @GetMapping("active")
  public ResponseEntity<List<AchievementResponse>> getActiveAchievements() {
    return ResponseEntity.ok(achievementService.findAchievementActive());
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createAchievement(@RequestBody @Valid AchievementCreateDto achievementCreateDto) {
    return ResponseEntity.ok(
        achievementService.createAchievement(achievementCreateDto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateAchievement(@PathVariable Integer id, @RequestBody @Valid AchievementUpdateDto dto) {
    return ResponseEntity.ok(
        achievementService.updateAchievement(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteAchievement(@PathVariable Integer id) {
    return ResponseEntity.ok(
        achievementService.deleteAchievement(id)
    );
  }
}
