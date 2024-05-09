package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.QuestTurnCreateDto;
import com.wiinvent.gami.domain.dto.QuestTurnUpdateDto;
import com.wiinvent.gami.domain.response.QuestTurnResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.QuestTurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/quest-turn")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class QuestTurnController {

  @Autowired
  private QuestTurnService questTurnService;

  @GetMapping("")
  public ResponseEntity<PageResponse<QuestTurnResponse>> findAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer questNum,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(
        PageResponse.createFrom(questTurnService.findAll(name, questNum, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<QuestTurnResponse> getQuestTurnDetail(@PathVariable Long id) {
    return ResponseEntity.ok(questTurnService.getQuestTurnDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createQuestTurn(@RequestBody QuestTurnCreateDto dto) {
    return ResponseEntity.ok(
        questTurnService.createQuestTurn(dto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateQuest(@PathVariable Long id, @RequestBody QuestTurnUpdateDto dto) {
    return ResponseEntity.ok(
        questTurnService.updateQuestTurn(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteQuest(@PathVariable Long id) {
    return ResponseEntity.ok(
        questTurnService.deleteQuestTurn(id)
    );
  }
}
