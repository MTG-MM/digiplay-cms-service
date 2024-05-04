package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.ChallengeCreateDto;
import com.wiinvent.gami.domain.dto.ChallengeUpdateDto;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.ChallengeResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.ChallengeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/challenge")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@Tag(name = "Challenge", description = "Api challenge")
public class ChallengeController {
  @Autowired
  ChallengeService challengeService;
  @GetMapping("")
  public ResponseEntity<PageResponse<ChallengeResponse>> findAll(
      @RequestParam(required = false) Integer gameId,
      @RequestParam(required = false) Status status,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ){
    return ResponseEntity.ok(
        PageResponse.createFrom(challengeService.findAll(gameId, status, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ChallengeResponse> getChallengeDetail(@PathVariable Integer id){
    return ResponseEntity.ok(challengeService.getChallengeDetails(id));
  }
  @PostMapping("")
  public ResponseEntity<Boolean> createChallenge(@RequestBody ChallengeCreateDto dto){
    return ResponseEntity.ok(
        challengeService.createChallenge(dto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateChallenge(@PathVariable Integer id, @RequestBody ChallengeUpdateDto dto){
    return ResponseEntity.ok(
        challengeService.updateChallenge(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteChallenge(@PathVariable Integer id){
    return ResponseEntity.ok(
        challengeService.deleteChallenge(id)
    );
  }
}
