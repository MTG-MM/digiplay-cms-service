package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.ChallengeDetailCreateDto;
import com.wiinvent.gami.domain.dto.ChallengeDetailUpdateDto;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.ChallengeDetailResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.ChallengeDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/challenge-detail")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@Tag(name = "Challenge detail", description = "Api challenge detail")
public class ChallengeDetailController {
  @Autowired
  ChallengeDetailService challengeDetailService;

  @GetMapping("")
  public ResponseEntity<PageResponse<ChallengeDetailResponse>> findAll(
      @RequestParam Integer challengeId,
      @RequestParam(required = false) Integer level,
      @RequestParam(required = false) Status status,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(
        PageResponse.createFrom(challengeDetailService.findAll(challengeId, level, status, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ChallengeDetailResponse> getChallengeDetail(@PathVariable Integer id) {
    return ResponseEntity.ok(challengeDetailService.getChallengeDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createChallengeDetail(@RequestBody @Valid ChallengeDetailCreateDto dto) {
    return ResponseEntity.ok(
        challengeDetailService.createChallengeDetail(dto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateChallengeDetail(@PathVariable Integer id, @RequestBody @Valid ChallengeDetailUpdateDto dto) {
    return ResponseEntity.ok(
        challengeDetailService.updateChallengeDetail(id, dto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteChallengeDetail(@PathVariable Integer id) {
    return ResponseEntity.ok(
        challengeDetailService.deleteChallengeDetail(id)
    );
  }
}
