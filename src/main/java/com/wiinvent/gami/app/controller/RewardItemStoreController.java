package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.RewardItemStoreCreateDto;
import com.wiinvent.gami.domain.dto.RewardItemStoreUpdateDto;
import com.wiinvent.gami.domain.response.RewardItemStoreResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.service.RewardItemStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vt/cms/reward-item-stores")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class RewardItemStoreController {

  @Autowired private RewardItemStoreService rewardItemStoreService;

  @GetMapping("")
  public ResponseEntity<PageResponse<RewardItemStoreResponse>> getRewardItemStores(Pageable pageable) {
    return ResponseEntity.ok(rewardItemStoreService.getAllRewardItemStores(pageable));
  }

  @GetMapping("type/{type}")
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
