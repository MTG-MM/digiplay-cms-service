package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.transaction.LuckyPointTransaction;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Log4j2
@Service
public class LuckyPointTransactionService extends BaseService {
  public PageCursorResponse<TransactionResponse> getLuckyPointTransaction
      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    CursorType type = CursorType.FIRST;
    List<LuckyPointTransaction> luckyPointTransactions = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      luckyPointTransactions = luckyPointTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      luckyPointTransactions = luckyPointTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      luckyPointTransactions = luckyPointTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
      luckyPointTransactions = luckyPointTransactions.stream().sorted(Comparator.comparingLong(LuckyPointTransaction::getCreatedAt).reversed()).toList();
    }
    List<TransactionResponse> responses = modelMapper.toLuckyPointTransactionResponse(luckyPointTransactions);
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
