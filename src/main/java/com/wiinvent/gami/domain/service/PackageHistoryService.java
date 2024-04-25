package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.entities.payment.PackageHistory;
import com.wiinvent.gami.domain.response.PackageHistoryResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class PackageHistoryService extends BaseService {
  public PageCursorResponse<PackageHistoryResponse> getPackageHistory(UUID userId, Long next, Long pre, int limit) {
    CursorType type = CursorType.FIRST;
    List<PackageHistory> packageHistories = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      packageHistories = packageHistoryStorage.findAll(userId, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      packageHistories = packageHistoryStorage.findAll(userId, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      packageHistories = packageHistoryStorage.findAll(userId, next, pre, limit, type);
      packageHistories = packageHistories.stream().sorted(Comparator.comparingLong(PackageHistory::getCreatedAt).reversed()).toList();
    }
    List<PackageHistoryResponse> responses = modelMapper.toPackageHistoryResponse(packageHistories);
    return new PageCursorResponse<>(responses, limit, type,"createdAt");
  }

//  public Boolean changePackageStatus(UUID id) {
//
//  }
}
