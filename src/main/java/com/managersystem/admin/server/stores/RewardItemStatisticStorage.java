package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardItemStatistic;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RewardItemStatisticStorage extends BaseStorage {
  public RewardItemStatistic findByDateAndRewardSegmentIdAndRewardItemId(LocalDate nowDateAtVn, Long rewardSegmentId, Long rewardItemId) {
    return rewardItemStatisticRepository.findByDateAndRewardSegmentIdAndRewardItemId(nowDateAtVn, rewardSegmentId, rewardItemId);
  }

  public void save(RewardItemStatistic rewardItemStatistic) {
    rewardItemStatisticRepository.save(rewardItemStatistic);
  }
}
