package com.wiinvent.gami.app.controller.user.Transaction;

import com.wiinvent.gami.domain.response.PaymentTransactionResponse;
import com.wiinvent.gami.domain.response.RewardItemHistoryResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.user.SubTransactionService;
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
public class SubTransactionController {
  @Autowired
  SubTransactionService subTransactionService;
  @GetMapping("sub")
  public ResponseEntity<PageCursorResponse<PaymentTransactionResponse>> getRewardItemHistory(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(subTransactionService.getPaymentTransaction(userId, next, pre, limit));
  }
}
