package com.wiinvent.gami.domain.service.user.Transaction;

import com.wiinvent.gami.domain.entities.payment.PaymentTransaction;
import com.wiinvent.gami.domain.response.PaymentTransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubTransactionService extends BaseService {
  public PageCursorResponse<PaymentTransactionResponse> getPaymentTransaction(UUID userId, Long next, Long pre, Integer limit){
    List<PaymentTransaction> paymentTransactionList = paymentTransactionStorage.findAll(userId, next, pre, limit);
    List<PaymentTransactionResponse> responses = modelMapper.toPaymentTransactionResponse(paymentTransactionList);
    return new PageCursorResponse<>(responses, limit, next, pre, "createdAt");
  }
}
