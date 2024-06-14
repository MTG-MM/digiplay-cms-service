package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.Collection;
import com.wiinvent.gami.domain.entities.transaction.CollectionTransaction;
import com.wiinvent.gami.domain.entities.user.UserCollection;
import com.wiinvent.gami.domain.response.CollectionTransactionResponse;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CollectionTransactionService extends BaseService {
  public PageCursorResponse<CollectionTransactionResponse> getCollectionTransaction
      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    CursorType type = CursorType.FIRST;
    List<CollectionTransaction> collectionTransactions = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      collectionTransactions = collectionTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      collectionTransactions = collectionTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      collectionTransactions = collectionTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
      collectionTransactions = collectionTransactions.stream().sorted(Comparator.comparingLong(CollectionTransaction::getCreatedAt).reversed()).toList();
    }
    List<CollectionTransactionResponse> responses = modelMapper.toCollectionTransactionResponse(collectionTransactions);
    List<Long> collectionIds = collectionTransactions.stream().map(CollectionTransaction::getCollectionId).toList();
    Map<Long, Collection> collectionMap = collectionStorage.findAllCollectionByIdIn(collectionIds).stream()
        .collect(Collectors.toMap(Collection::getId, Function.identity()));
    responses.forEach(r -> {
      Collection collection = collectionMap.getOrDefault(r.getCollectionId(), null);
      r.setCollectionName(collection != null ? collection.getName() : null);
    });
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
