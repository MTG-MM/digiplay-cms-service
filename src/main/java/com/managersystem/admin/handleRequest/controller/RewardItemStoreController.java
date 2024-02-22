package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.RewardItemStoreCreateDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardItemStoreUpdateDto;
import com.managersystem.admin.handleRequest.controller.response.RewardItemStoreResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.type.StoreType;
import com.managersystem.admin.server.service.RewardItemStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mos/cms/reward-item-stores")
@CrossOrigin(origins = "*")
public class RewardItemStoreController {

  @Autowired private RewardItemStoreService rewardItemStoreService;

  @GetMapping("")
  public ResponseEntity<PageResponse<RewardItemStoreResponse>> getRewardItemStores(Pageable pageable) {
    return ResponseEntity.ok(rewardItemStoreService.getAllRewardItemStores(pageable));
  }

  @GetMapping("{type}")
  public ResponseEntity<List<RewardItemStoreResponse>> getAllRewardItemStores(@PathVariable StoreType type) {
    return ResponseEntity.ok(rewardItemStoreService.getAllRewardItemStores(type));
  }

  @GetMapping("{id}")
  public ResponseEntity<RewardItemStoreResponse> getRewardItemStoreDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardItemStoreService.getRewardItemStoreDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createRewardItemStores(@RequestBody RewardItemStoreCreateDto rewardItemStoreDto) {
    return ResponseEntity.ok(rewardItemStoreService.createRewardItemStores(rewardItemStoreDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateRewardItemStores(@PathVariable Long id, @RequestBody RewardItemStoreUpdateDto rewardItemStoreDto) {
    return ResponseEntity.ok(rewardItemStoreService.updateRewardItemStores(id, rewardItemStoreDto));
  }
}
