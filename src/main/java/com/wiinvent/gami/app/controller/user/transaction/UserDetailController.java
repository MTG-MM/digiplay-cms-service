package com.wiinvent.gami.app.controller.user.transaction;

import com.wiinvent.gami.domain.response.CharacterUserTransactionResponse;
import com.wiinvent.gami.domain.response.PackageHistoryResponse;
import com.wiinvent.gami.domain.response.RewardItemHistoryResponse;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.service.PackageHistoryService;
import com.wiinvent.gami.domain.service.reward.RewardItemHistoryService;
import com.wiinvent.gami.domain.service.user.transaction.*;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/vt/cms/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserDetailController {
  @Autowired PackageHistoryService packageHistoryService;
  @Autowired CoinTransactionService coinTransactionService;
  @Autowired PointTransactionService pointTransactionService;
  @Autowired ExpHistoryService expHistoryService;
  @Autowired TurnTransactionService turnTransactionService;
  @Autowired RewardItemHistoryService rewardItemHistoryService;
  @Autowired CharacterUserTransactionService characterUserTransactionService;

  @GetMapping("sub")
  public ResponseEntity<PageCursorResponse<PackageHistoryResponse>> getPackageHistory (
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(packageHistoryService.getPackageHistory(userId, next, pre, limit));
  }

  @GetMapping("/transaction/coin")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getCoinTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(coinTransactionService.getCoinTransaction(userId, next, pre, limit));
  }

  @GetMapping("/transaction/point")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getPointTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(pointTransactionService.getPointTransaction(userId, next, pre, limit));
  }

  @GetMapping("/transaction/exp")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getExpHistoryTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(expHistoryService.getExpHistories(userId, next, pre, limit));
  }

  @GetMapping("/transaction/turn")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getTurnTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(turnTransactionService.getTurnTransaction(userId, next, pre, limit));
  }

  @GetMapping("reward")
  public ResponseEntity<PageCursorResponse<RewardItemHistoryResponse>> getRewardItemHistory(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(rewardItemHistoryService.getRewardItemHistory(userId, next, pre, limit));
  }

  @GetMapping("/transaction/user-character")
  public ResponseEntity<PageCursorResponse<CharacterUserTransactionResponse>> getUserCharacterTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(characterUserTransactionService.getCharacterUserTransaction(userId, next, pre, limit));
  }
}
