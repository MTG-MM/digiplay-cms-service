package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.transaction.TicketHistory;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Log4j2
public class TicketHistoryService extends BaseService {
  public PageCursorResponse<TransactionResponse> getTicketHistory
      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    CursorType type = CursorType.FIRST;
    List<TicketHistory> ticketHistories = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      ticketHistories = ticketHistoryStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      ticketHistories = ticketHistoryStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      ticketHistories = ticketHistoryStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
      ticketHistories = ticketHistories.stream().sorted(Comparator.comparingLong(TicketHistory::getCreatedAt).reversed()).toList();
    }
    List<TransactionResponse> responses = modelMapper.toTicketHistoryResponse(ticketHistories);
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
