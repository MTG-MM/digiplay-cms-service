package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.RewardScheduleDto;
import com.managersystem.admin.handleRequest.controller.response.RewardScheduleResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.service.RewardScheduleService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mos/cms/reward-schedule")
@CrossOrigin(origins = "*")
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
  public ResponseEntity<Boolean> updateRewardSchedules(@PathVariable Long id, @RequestBody RewardScheduleDto rewardScheduleDto) {
    return ResponseEntity.ok(rewardScheduleService.updateRewardSchedules(id, rewardScheduleDto));
  }
}
