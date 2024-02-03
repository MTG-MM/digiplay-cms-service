package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.RewardItemDto;
import com.managersystem.admin.handleRequest.controller.response.RewardItemResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.service.RewardItemService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mos/cms/reward-items")
@CrossOrigin(origins = "*")
public class RewardItemController {

  @Autowired
  private RewardItemService rewardItemService;

  @GetMapping("")
  public ResponseEntity<PageResponse<RewardItemResponse>> getRewardItems(Pageable pageable) {
    return ResponseEntity.ok(rewardItemService.getAllRewardItems(pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<RewardItemResponse> getRewardItemDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardItemService.getRewardItemDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createRewardItems(@RequestBody RewardItemDto rewardItemDto) {
    return ResponseEntity.ok(rewardItemService.createRewardItems(rewardItemDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateRewardItems(@PathVariable Long id, @RequestBody RewardItemDto rewardItemDto) {
    return ResponseEntity.ok(rewardItemService.updateRewardItems(id, rewardItemDto));
  }
}
