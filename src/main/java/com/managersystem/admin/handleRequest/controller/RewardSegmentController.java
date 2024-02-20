package com.managersystem.admin.handleRequest.controller;


import com.managersystem.admin.handleRequest.controller.dto.ChooseRewardItemSegmentDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDto;
import com.managersystem.admin.handleRequest.controller.response.RewardSegmentResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.service.RewardSegmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mos/cms/reward-segment")
@CrossOrigin(origins = "*")
public class RewardSegmentController {

  @Autowired
  private RewardSegmentService rewardSegmentService;

  @GetMapping("")
  public ResponseEntity<PageResponse<RewardSegmentResponse>> getRewardSegments(Pageable pageable) {
    return ResponseEntity.ok(rewardSegmentService.getAllRewardSegments(pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<RewardSegmentResponse> getRewardSegmentDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardSegmentService.getRewardSegmentDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Long> createRewardSegments(@RequestBody RewardSegmentDto rewardSegmentDto) {
    return ResponseEntity.ok(rewardSegmentService.createRewardSegments(rewardSegmentDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateRewardSegments(@PathVariable Long id, @RequestBody RewardSegmentDto rewardSegmentDto) {
    return ResponseEntity.ok(rewardSegmentService.updateRewardSegments(id, rewardSegmentDto));
  }

  @PostMapping("chooseRwItem/{id}")
  public ResponseEntity<Boolean> chooseRwItem(@PathVariable(name = "id") Long rwSegmentId,
                                              @RequestBody @Valid ChooseRewardItemSegmentDto dto){
    return ResponseEntity.ok(rewardSegmentService.chooseRwItem(rwSegmentId, dto.getIds()));
  }
}
