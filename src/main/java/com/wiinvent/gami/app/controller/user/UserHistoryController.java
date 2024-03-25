package com.wiinvent.gami.app.controller.user;

import com.wiinvent.gami.app.controller.BaseController;
import com.wiinvent.gami.domain.response.RewardItemHistoryResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.reward.RewardItemHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/vt/cms/user/history")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserHistoryController extends BaseController {

  @Autowired RewardItemHistoryService rewardItemHistoryService;

  @GetMapping("reward")
  public ResponseEntity<PageCursorResponse<RewardItemHistoryResponse>> getRewardItemHistory(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
      ) {
    return ResponseEntity.ok(rewardItemHistoryService.getRewardItemHistory(userId, next, pre, limit));
  }
}
