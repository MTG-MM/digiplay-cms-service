package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardState;
import com.managersystem.admin.server.stores.base.BaseStorage;
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
