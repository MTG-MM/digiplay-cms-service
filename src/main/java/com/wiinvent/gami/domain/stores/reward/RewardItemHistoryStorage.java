package com.wiinvent.gami.domain.stores.reward;

import com.wiinvent.gami.domain.entities.reward.RewardItemHistory;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

@Component
public class RewardItemHistoryStorage extends BaseStorage {

  public void save(RewardItemHistory rewardItemHistory) {
    rewardItemHistoryRepository.save(rewardItemHistory);
  }

  public Integer countUsersInCreatedAtBetween(Long rewardSegmentId, Long rewardItemId, long startDateAtVn, long endDateAtVn) {
    return rewardItemHistoryRepository.countUsersInCreatedAtBetween(rewardSegmentId, rewardItemId, startDateAtVn, endDateAtVn);
  }

  public Integer countRewardItemReceivedInCreatedAtBetween(Long rewardSegmentId, Long rewardItemId, long startDateAtVn, long endDateAtVn) {
    return rewardItemHistoryRepository.countRewardItemReceivedInCreatedAtBetween(rewardSegmentId, rewardItemId, startDateAtVn, endDateAtVn);
  }
}
