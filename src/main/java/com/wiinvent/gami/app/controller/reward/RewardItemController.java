package com.wiinvent.gami.app.controller.reward;

import com.wiinvent.gami.domain.dto.ProcessQuantityDto;
import com.wiinvent.gami.domain.dto.RewardItemDto;
import com.wiinvent.gami.domain.dto.RewardItemUpdateDto;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.response.RewardItemResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.reward.RewardItemService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
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
@RequestMapping("v1/portal/reward-items")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class RewardItemController {

  @Autowired
  private RewardItemService rewardItemService;

//  @GetMapping("")
//  public ResponseEntity<PageResponse<RewardItemResponse>> getRewardItems(Pageable pageable) {
//    return ResponseEntity.ok(rewardItemService.getAllRewardItems(pageable));
//  }

  @GetMapping("")
  @PageableAsQueryParam
  public PageResponse<RewardItemResponse> getAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) Integer rewardTypeId,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      @Parameter(hidden = true) Pageable pageable) {
    return rewardItemService.getAll(id, name, rewardTypeId,pageable);
  }

  @GetMapping("{id}")
  public ResponseEntity<RewardItemResponse> getRewardItemDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardItemService.getRewardItemDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createRewardItems(@RequestBody @Valid RewardItemDto rewardItemDto) {
    return ResponseEntity.ok(rewardItemService.createRewardItems(rewardItemDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateRewardItems(@PathVariable Long id, @RequestBody @Valid RewardItemUpdateDto dto) {
    return ResponseEntity.ok(rewardItemService.updateRewardItems(id, dto));
  }

  @GetMapping("active")
  public ResponseEntity<List<RewardItemResponse>> getActiveRewardItems(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) RewardItemType type
  ) {
    return ResponseEntity.ok(rewardItemService.getRwItemActive(name, type));
  }

  @PutMapping("updateQuantity")
  public ResponseEntity<Boolean> updateQuantity(@RequestBody @Valid ProcessQuantityDto dto){
    return ResponseEntity.ok(rewardItemService.updateQuantity(dto));
  }
}
