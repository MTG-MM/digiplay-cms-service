package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.entities.RewardItem;
import com.wiinvent.gami.domain.entities.RewardItemHistory;
import com.wiinvent.gami.domain.entities.User;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.utils.JsonParser;
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
    rewardItemHistory.setRewardItemId(rewardItem.getId());
    rewardItemHistoryStorage.save(rewardItemHistory);
  }
}
