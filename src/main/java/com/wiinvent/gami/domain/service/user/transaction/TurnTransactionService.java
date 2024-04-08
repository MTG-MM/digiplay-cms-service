package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.transaction.TurnTransaction;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TurnTransactionService extends BaseService {
  public PageCursorResponse<TransactionResponse> getTurnTransaction(UUID userId, Long next, Long pre, int limit){
    List<TurnTransaction> pointTransactions = turnTransactionStorage.findAll(userId, next, pre, limit);
    List<TransactionResponse> responses = modelMapper.toTurnTransactionResponse(pointTransactions);
    return new PageCursorResponse<>(responses, limit, next, pre, "createdAt");
  }
}
