package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.app.controller.dto.RewardItemDto;
import com.wiinvent.gami.app.controller.response.RewardItemResponse;
import com.wiinvent.gami.app.controller.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.service.RewardItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/reward-items")
public class RewardItemController {

  @Autowired
  private RewardItemService rewardItemService;

//  @GetMapping("")
//  public ResponseEntity<PageResponse<RewardItemResponse>> getRewardItems(Pageable pageable) {
//    return ResponseEntity.ok(rewardItemService.getAllRewardItems(pageable));
//  }

  @GetMapping("")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
  public PageResponse<RewardItemResponse> getAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) RewardItemType type,
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.DESC),
      })
      Pageable pageable) {
    return rewardItemService.getAll(id, name, type,pageable);
  }

  @GetMapping("{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
  public ResponseEntity<RewardItemResponse> getRewardItemDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardItemService.getRewardItemDetail(id));
  }

  @PostMapping("")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
  public ResponseEntity<Long> createRewardItems(@RequestBody RewardItemDto rewardItemDto) {
    return ResponseEntity.ok(rewardItemService.createRewardItems(rewardItemDto));
  }

  @PutMapping("{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
  public ResponseEntity<Boolean> updateRewardItems(@PathVariable Long id, @RequestBody @Valid RewardItemDto rewardItemDto) {
    return ResponseEntity.ok(rewardItemService.updateRewardItems(id, rewardItemDto));
  }
}
