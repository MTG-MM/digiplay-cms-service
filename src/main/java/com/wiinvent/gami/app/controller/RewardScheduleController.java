package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.RewardScheduleDto;
import com.wiinvent.gami.domain.dto.RewardScheduleUpdateDto;
import com.wiinvent.gami.domain.response.RewardScheduleResponse;
import com.wiinvent.gami.domain.service.RewardScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vt/cms/reward-schedule")
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
  public ResponseEntity<Boolean> createRewardSchedules(@RequestBody RewardScheduleDto rewardScheduleDto) {
    return ResponseEntity.ok(rewardScheduleService.createRewardSchedules(rewardScheduleDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateRewardSchedules(@PathVariable Long id, @RequestBody RewardScheduleUpdateDto rewardScheduleDto) {
    return ResponseEntity.ok(rewardScheduleService.updateRewardSchedules(id, rewardScheduleDto));
  }
}
