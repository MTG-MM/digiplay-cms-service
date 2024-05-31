package com.wiinvent.gami.app.controller.reward;

import com.wiinvent.gami.domain.dto.RewardScheduleDto;
import com.wiinvent.gami.domain.dto.RewardScheduleUpdateDto;
import com.wiinvent.gami.domain.response.RewardScheduleResponse;
import com.wiinvent.gami.domain.service.reward.RewardScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/portal/reward-schedule")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class RewardScheduleController {

  @Autowired
  private RewardScheduleService rewardScheduleService;

  @GetMapping("")
  public ResponseEntity<List<RewardScheduleResponse>> getRewardSchedules(@RequestParam Long rewardSegmentId) {
    return ResponseEntity.ok(rewardScheduleService.getAllRewardSchedules(rewardSegmentId));
  }

  @GetMapping("{id}")
  public ResponseEntity<RewardScheduleResponse> getRewardScheduleDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardScheduleService.getRewardScheduleDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createRewardSchedules(@RequestBody @Valid RewardScheduleDto rewardScheduleDto) {
    return ResponseEntity.ok(rewardScheduleService.createRewardSchedules(rewardScheduleDto));
  }

  @PutMapping("{rewardSegmentDetailId}")
  public ResponseEntity<Boolean> updateRewardSchedules(@PathVariable Long rewardSegmentDetailId, @RequestBody @Valid List<RewardScheduleUpdateDto> rewardScheduleUpdateDtos) {
    return ResponseEntity.ok(rewardScheduleService.updateRewardSchedules(rewardSegmentDetailId, rewardScheduleUpdateDtos));
  }
}
