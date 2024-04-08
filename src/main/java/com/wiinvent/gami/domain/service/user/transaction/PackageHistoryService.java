package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.payment.PackageHistory;
import com.wiinvent.gami.domain.response.PackageHistoryResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PackageHistoryService extends BaseService {
  public PageCursorResponse<PackageHistoryResponse> getPackageHistory(UUID userId, Long next, Long pre, Integer limit){
    List<PackageHistory> packageHistoryList = packageHistoryStorage.findAll(userId, next, pre, limit);
    List<PackageHistoryResponse> responses = modelMapper.toPackageHistoryResponse(packageHistoryList);
    return new PageCursorResponse<>(responses, limit, next, pre, "createdAt");
  }
}
