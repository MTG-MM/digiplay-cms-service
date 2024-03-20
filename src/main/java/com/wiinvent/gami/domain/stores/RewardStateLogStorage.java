package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.reward.RewardState;
import com.wiinvent.gami.domain.entities.reward.RewardStateLog;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

@Component
public class RewardStateLogStorage extends BaseStorage {

  public RewardState findById(Long id) {
    return rewardStateRepository.findById(id).orElse(null);
  }

  public void save(RewardStateLog rewardStateLog) {
    rewardStateLogRepository.save(rewardStateLog);
  }
}
