package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.QuestCreateDto;
import com.wiinvent.gami.domain.dto.QuestUpdateDto;
import com.wiinvent.gami.domain.response.QuestResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/quest")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class QuestController {
  @Autowired
  private QuestService questService;
  @GetMapping("")
  public ResponseEntity<PageResponse<QuestResponse>> findAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code ,
      @RequestParam(required = false) Integer gameId,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(
        PageResponse.createFrom(questService.findAll(code, name, gameId, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<QuestResponse> getQuestDetail(@PathVariable Long id) {
    return ResponseEntity.ok(questService.getQuestDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createQuest(@RequestBody QuestCreateDto questCreateDto) {
    return ResponseEntity.ok(
        questService.createQuest(questCreateDto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateQuest(@PathVariable Long id, @RequestBody QuestUpdateDto dto) {
    return ResponseEntity.ok(
        questService.updateQuest(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteQuest(@PathVariable Long id) {
    return ResponseEntity.ok(
        questService.deleteQuest(id)
    );
  }
}
