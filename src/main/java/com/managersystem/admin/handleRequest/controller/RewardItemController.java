package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.RewardItemDto;
import com.managersystem.admin.handleRequest.controller.response.RewardItemResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.service.RewardItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mos/cms/reward-items")
@CrossOrigin(origins = "*")
public class RewardItemController {

  @Autowired
  private RewardItemService rewardItemService;

//  @GetMapping("")
//  public ResponseEntity<PageResponse<RewardItemResponse>> getRewardItems(Pageable pageable) {
//    return ResponseEntity.ok(rewardItemService.getAllRewardItems(pageable));
//  }

  @GetMapping("")
  public PageResponse<RewardItemResponse> getAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) RewardType type,
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.DESC),
      })
      Pageable pageable) {
    return rewardItemService.getAll(id, name, type,pageable);
  }

  @GetMapping("{id}")
  public ResponseEntity<RewardItemResponse> getRewardItemDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardItemService.getRewardItemDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Long> createRewardItems(@RequestBody RewardItemDto rewardItemDto) {
    return ResponseEntity.ok(rewardItemService.createRewardItems(rewardItemDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateRewardItems(@PathVariable Long id, @RequestBody @Valid RewardItemDto rewardItemDto) {
    return ResponseEntity.ok(rewardItemService.updateRewardItems(id, rewardItemDto));
  }
}
