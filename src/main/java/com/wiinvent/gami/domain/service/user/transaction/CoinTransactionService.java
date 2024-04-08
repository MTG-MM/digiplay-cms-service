package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.transaction.CoinTransaction;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoinTransactionService extends BaseService {
  public PageCursorResponse<TransactionResponse> getCoinTransaction(UUID userId, Long next, Long pre, int limit) {
    List<CoinTransaction> coinTransactions = coinTransactionStorage.findAll(userId, next, pre, limit);
    List<TransactionResponse> responses = modelMapper.toCoinTransactionResponse(coinTransactions);
    return new PageCursorResponse<>(responses, limit, next, pre, "createdAt");
  }
}
