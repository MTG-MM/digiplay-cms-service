package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.transaction.CoinTransaction;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class CoinTransactionService extends BaseService {
  public PageCursorResponse<TransactionResponse> getCoinTransaction(UUID userId, Long next, Long pre, int limit) {
    CursorType type = CursorType.FIRST;
    List<CoinTransaction> coinTransactions = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      coinTransactions = coinTransactionStorage.findAll(userId, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      coinTransactions = coinTransactionStorage.findAll(userId, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      coinTransactions = coinTransactionStorage.findAll(userId, next, pre, limit, type);
      coinTransactions = coinTransactions.stream().sorted(Comparator.comparingLong(CoinTransaction::getCreatedAt).reversed()).toList();
    }
    List<TransactionResponse> responses = modelMapper.toCoinTransactionResponse(coinTransactions);
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
