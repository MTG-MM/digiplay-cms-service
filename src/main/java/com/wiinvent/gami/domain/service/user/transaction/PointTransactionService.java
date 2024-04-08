package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.transaction.PointTransaction;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PointTransactionService extends BaseService {
  public PageCursorResponse<TransactionResponse> getPointTransaction(UUID userId, Long next, Long pre, int limit){
    List<PointTransaction> pointTransactions = pointTransactionStorage.findAll(userId, next, pre, limit);
    List<TransactionResponse> responses = modelMapper.toPointTransactionResponse(pointTransactions);
    return new PageCursorResponse<>(responses, limit, next, pre, "createdAt");
  }
}
