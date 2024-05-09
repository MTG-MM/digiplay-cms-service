package com.wiinvent.gami.app.controller.reward;


import com.wiinvent.gami.domain.dto.ChooseRewardItemSegmentDto;
import com.wiinvent.gami.domain.dto.RewardSegmentDto;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.RewardSegmentResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.reward.RewardSegmentService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/reward-segment")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class RewardSegmentController {

  @Autowired
  private RewardSegmentService rewardSegmentService;

  @GetMapping("")
  public ResponseEntity<PageResponse<RewardSegmentResponse>> getRewardSegments(
      @RequestParam(required = false) String code,
      @RequestParam(required = false) Status status,
      @Parameter(hidden = true)
      @SortDefault(sort = "updatedAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(rewardSegmentService.getAllRewardSegments(code, status, pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<RewardSegmentResponse> getRewardSegmentDetail(@PathVariable Long id) {
    return ResponseEntity.ok(rewardSegmentService.getRewardSegmentDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createRewardSegments(@RequestBody RewardSegmentDto rewardSegmentDto) {
    return ResponseEntity.ok(rewardSegmentService.createRewardSegments(rewardSegmentDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateRewardSegments(@PathVariable Long id, @RequestBody RewardSegmentDto rewardSegmentDto) {
    return ResponseEntity.ok(rewardSegmentService.updateRewardSegments(id, rewardSegmentDto));
  }

  @PostMapping("chooseRwItem/{id}")
  public ResponseEntity<Boolean> chooseRwItem(@PathVariable(name = "id") Long rwSegmentId,
                                              @RequestBody @Valid ChooseRewardItemSegmentDto dto) {
    return ResponseEntity.ok(rewardSegmentService.chooseRwItem(rwSegmentId, dto.getIds()));
  }
}
