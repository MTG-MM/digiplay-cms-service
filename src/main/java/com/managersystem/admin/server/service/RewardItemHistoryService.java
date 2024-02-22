package com.managersystem.admin.server.service;

import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.entities.RewardItemHistory;
import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.JsonParser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RewardItemHistoryService extends BaseService {

  public void createRewardItemHistory(User user, RewardItem rewardItem, UUID itemDetailId, Long rewardSegmentId, String note) {
    RewardItemHistory rewardItemHistory = new RewardItemHistory();
    rewardItemHistory.setName(rewardItem.getRewardName());
    rewardItemHistory.setType(rewardItem.getRewardType());
    rewardItemHistory.setNote(note);
    rewardItemHistory.setUserId(user.getId());
    rewardItemHistory.setRewardSegmentId(rewardSegmentId);
    rewardItemHistory.setImageUrl(rewardItem.getImageUrl());
    rewardItemHistory.setRewardInfo(JsonParser.toJson(rewardItem));
    rewardItemHistory.setRewardItemDetailId(itemDetailId);
    rewardItemHistoryStorage.save(rewardItemHistory);
  }
}
