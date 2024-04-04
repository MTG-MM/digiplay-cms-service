package com.wiinvent.gami.app.controller.user.Transaction;

import com.wiinvent.gami.domain.response.PaymentTransactionResponse;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.service.user.Transaction.CoinTransactionService;
import com.wiinvent.gami.domain.service.user.Transaction.ExpHistoryService;
import com.wiinvent.gami.domain.service.user.Transaction.PointTransactionService;
import com.wiinvent.gami.domain.service.user.Transaction.SubTransactionService;
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
  @Autowired SubTransactionService subTransactionService;
  @Autowired CoinTransactionService coinTransactionService;
  @Autowired PointTransactionService pointTransactionService;
  @Autowired ExpHistoryService expHistoryService;
  @GetMapping("sub")
  public ResponseEntity<PageCursorResponse<PaymentTransactionResponse>> getSubTransaction (
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(subTransactionService.getPaymentTransaction(userId, next, pre, limit));
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
}
