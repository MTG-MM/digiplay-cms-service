package com.wiinvent.gami.domain.service.reward;

import com.wiinvent.gami.domain.entities.reward.RewardItemHistory;
import com.wiinvent.gami.domain.response.RewardItemHistoryResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RewardItemHistoryService extends BaseService {


  public PageCursorResponse<RewardItemHistoryResponse> getRewardItemHistory(UUID userId, Long next, Long pre, int limit) {
    CursorType type;
    List<RewardItemHistory> rewardItemHistories = rewardItemHistoryStorage.findAll(userId, next, pre, limit);
    List<RewardItemHistoryResponse> responses = modelMapper.toListRewardItemHistoryResponse(rewardItemHistories);
    if (next == null && pre == null) {
      type = CursorType.FIRST;
    } else if (next != null) {
      type = CursorType.NEXT;
    } else {
      type = CursorType.PRE;
    }
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
