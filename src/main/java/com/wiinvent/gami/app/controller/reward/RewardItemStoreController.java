package com.wiinvent.gami.app.controller.reward;

import com.wiinvent.gami.domain.dto.RewardItemStoreCreateDto;
import com.wiinvent.gami.domain.dto.RewardItemStoreUpdateDto;
import com.wiinvent.gami.domain.response.RewardItemStoreResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.service.reward.RewardItemStoreService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/portal/reward-item-stores")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class RewardItemStoreController {

  @Autowired
  private RewardItemStoreService rewardItemStoreService;

  @GetMapping("")
  @PageableAsQueryParam
  public ResponseEntity<PageResponse<RewardItemStoreResponse>> getRewardItemStores(
      @RequestParam(required = false) StoreType type,
      @RequestParam(required = false) String name,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      @Parameter(hidden = true)
      Pageable pageable) {
    return ResponseEntity.ok(rewardItemStoreService.getAllRewardItemStores(type, name, pageable));
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

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteRewardItemStores(@PathVariable Long id) {
    return ResponseEntity.ok(rewardItemStoreService.deleteRewardItemStore(id));
  }
}
