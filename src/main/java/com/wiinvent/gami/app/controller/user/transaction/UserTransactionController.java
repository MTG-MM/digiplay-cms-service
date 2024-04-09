package com.wiinvent.gami.app.controller.user.transaction;

import com.wiinvent.gami.domain.response.GameTournamentUserResponse;
import com.wiinvent.gami.domain.response.PackageHistoryResponse;
import com.wiinvent.gami.domain.response.TransactionResponse;
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
@RequestMapping("api/vt/cms/user/transaction")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserTransactionController {
  @Autowired
  PackageHistoryService packageHistoryService;
  @Autowired CoinTransactionService coinTransactionService;
  @Autowired PointTransactionService pointTransactionService;
  @Autowired ExpHistoryService expHistoryService;
  @Autowired TurnTransactionService turnTransactionService;
  @Autowired GameTournamentUserService gameTournamentUserService;
  @GetMapping("sub")
  public ResponseEntity<PageCursorResponse<PackageHistoryResponse>> getPackageHistory (
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(packageHistoryService.getPackageHistory(userId, next, pre, limit));
  }

  @GetMapping("coin")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getCoinTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(coinTransactionService.getCoinTransaction(userId, next, pre, limit));
  }

  @GetMapping("point")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getPointTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(pointTransactionService.getPointTransaction(userId, next, pre, limit));
  }

  @GetMapping("exp")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getExpHistoryTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(expHistoryService.getExpHistories(userId, next, pre, limit));
  }

  @GetMapping("turn")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getTurnTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(turnTransactionService.getTurnTransaction(userId, next, pre, limit));
  }

  @GetMapping("game-tournament")
  public ResponseEntity<PageCursorResponse<GameTournamentUserResponse>> getGameTournament (
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(gameTournamentUserService.getGameTournament(userId, next, pre, limit));
  }
}
