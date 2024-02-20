package com.managersystem.admin.handleRequest.controller;


import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailDto;
import com.managersystem.admin.handleRequest.controller.response.RewardSegmentDetailResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.service.RewardSegmentDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mos/cms/reward-segment-detail")
@CrossOrigin(origins = "*")
public class RewardSegmentDetailController {

  @Autowired
  private RewardSegmentDetailService rewardSegmentDetailService;

  @GetMapping("")
  public ResponseEntity<List<RewardSegmentDetailResponse>> getRewardSegmentDetails(@RequestParam Long rewardSegmentId) {
    return ResponseEntity.ok(rewardSegmentDetailService.getAllRewardSegmentDetails(rewardSegmentId));
  }

  @PutMapping("update")
  public ResponseEntity<Boolean> update(@RequestParam Long rwSegmentId, @RequestBody List<@Valid RewardSegmentDetailDto> rewardPoolDetailDtos){
    return ResponseEntity.ok(rewardSegmentDetailService.updateRewardSegmentDetails(rwSegmentId, rewardPoolDetailDtos));
  }

  @GetMapping("{id}")
  public ResponseEntity<RewardSegmentDetailResponse> getRewardSegmentDetailDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardSegmentDetailService.getRewardSegmentDetailDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createRewardSegmentDetails(@RequestBody RewardSegmentDetailDto rewardSegmentDetailDto) {
    return ResponseEntity.ok(rewardSegmentDetailService.createRewardSegmentDetails(rewardSegmentDetailDto));
  }

//  @PutMapping("{id}")
//  public ResponseEntity<Boolean> updateRewardSegmentDetails(@PathVariable Long id, @RequestBody RewardSegmentDetailDto rewardSegmentDetailDto) {
//    return ResponseEntity.ok(rewardSegmentDetailService.updateRewardSegmentDetails(id, rewardSegmentDetailDto));
//  }
}
