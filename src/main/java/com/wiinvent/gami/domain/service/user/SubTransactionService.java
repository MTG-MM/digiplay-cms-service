package com.wiinvent.gami.domain.service.user;

import com.wiinvent.gami.domain.entities.payment.PaymentTransaction;
import com.wiinvent.gami.domain.response.PaymentTransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubTransactionService extends BaseService {
  public PageCursorResponse<PaymentTransactionResponse> getPaymentTransaction(UUID userId, Long next, Long pre, int limit) {
    List<PaymentTransaction> paymentTransactions = paymentTransactionStorage.findAll(userId, next, pre, limit);
    List<PaymentTransactionResponse> responses = modelMapper.toPaymentTransactionResponse(paymentTransactions);
    return new PageCursorResponse<>(responses, limit, next, pre, "createdAt");
  }
}
