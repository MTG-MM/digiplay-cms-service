package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.transaction.TurnTransaction;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TurnTransactionService extends BaseService {
  public PageCursorResponse<TransactionResponse> getTurnTransaction
      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    List<TurnTransaction> turnTransactions = new ArrayList<>();
    CursorType type = CursorType.FIRST;
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      turnTransactions = turnTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (next == null) {
      type = CursorType.NEXT;
      pre = 0L;
      turnTransactions = turnTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (pre == null) {
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      turnTransactions = turnTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    }
    List<TransactionResponse> responses = modelMapper.toTurnTransactionResponse(turnTransactions);
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
