package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.RewardState;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

@Component
public class RewardStateStorage extends BaseStorage {

  public RewardState findById(Long id) {
    return rewardStateRepository.findById(id).orElse(null);
  }

  public void save(RewardState rewardState) {
    rewardStateRepository.save(rewardState);
  }
}
