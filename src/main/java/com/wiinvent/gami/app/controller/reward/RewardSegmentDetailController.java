package com.wiinvent.gami.app.controller.reward;


import com.wiinvent.gami.domain.dto.RewardSegmentDetailDto;
import com.wiinvent.gami.domain.dto.RewardSegmentDetailsUpdateDto;
import com.wiinvent.gami.domain.response.RewardSegmentDetailResponse;
import com.wiinvent.gami.domain.service.reward.RewardSegmentDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/vt/cms/reward-segment-detail")
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

  @PutMapping("publish/{rewardSegmentId}")
  public ResponseEntity<Boolean> publishRewardSegmentDetails(@PathVariable Long rewardSegmentId,@RequestBody List<RewardSegmentDetailsUpdateDto> rewardSegmentDetailDto) {
    return ResponseEntity.ok(rewardSegmentDetailService.publishRewardSegmentDetails(rewardSegmentId, rewardSegmentDetailDto));
  }

//  @PutMapping("{id}")
//  public ResponseEntity<Boolean> updateRewardSegmentDetails(@PathVariable Long id, @RequestBody RewardSegmentDetailDto rewardSegmentDetailDto) {
//    return ResponseEntity.ok(rewardSegmentDetailService.updateRewardSegmentDetails(id, rewardSegmentDetailDto));
//  }
}
