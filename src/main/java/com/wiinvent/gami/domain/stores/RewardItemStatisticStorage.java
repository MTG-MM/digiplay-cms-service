package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.RewardItemStatistic;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
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
