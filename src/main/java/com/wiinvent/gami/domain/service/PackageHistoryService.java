package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.entities.PremiumState;
import com.wiinvent.gami.domain.entities.SubState;
import com.wiinvent.gami.domain.entities.payment.PackageHistory;
import com.wiinvent.gami.domain.entities.type.PackageStateType;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.PackageHistoryResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PackageHistoryService extends BaseService {
  public PageCursorResponse<PackageHistoryResponse> getPackageHistory(UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    CursorType type = CursorType.FIRST;
    List<PackageHistory> packageHistories = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      packageHistories = packageHistoryStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      packageHistories = packageHistoryStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      packageHistories = packageHistoryStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
      packageHistories = packageHistories.stream().sorted(Comparator.comparingLong(PackageHistory::getCreatedAt).reversed()).toList();
    }
    List<PackageHistoryResponse> responses = modelMapper.toPackageHistoryResponse(packageHistories);
    return new PageCursorResponse<>(responses, limit, type,"createdAt");
  }

  public Boolean changePackageStatus(UUID id) {
    PackageHistory packageHistory = packageHistoryStorage.findById(id);
    SubState subState = subStateStorage.findByPackageId(packageHistory.getPackageInfo().getId());
    if (Objects.isNull(subState)) {
      PremiumState premiumState = premiumStateStorage.findByPackageId(packageHistory.getPackageInfo().getId());
      if (Objects.isNull(premiumState)) {
        throw new BadRequestException("Cannot find any package state");
      }
      premiumState.setPremiumState(PackageStateType.CANCEL);
    } else {
      subState.setSubState(PackageStateType.CANCEL);
    }
    return true;
  }
}
