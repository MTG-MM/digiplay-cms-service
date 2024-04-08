package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.transaction.ExpHistory;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpHistoryService extends BaseService {
  public PageCursorResponse<TransactionResponse> getExpHistories(UUID userId, Long next, Long pre, int limit){
    List<ExpHistory> expHistories = expHistoryStorage.findAll(userId, next , pre, limit);
    List<TransactionResponse> responses = modelMapper.toExpHistoryResponse(expHistories);
    return new PageCursorResponse<>(responses, limit, next, pre, "createdAt");
  }
}
