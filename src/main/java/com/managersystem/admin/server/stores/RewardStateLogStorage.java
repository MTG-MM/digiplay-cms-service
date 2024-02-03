package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardState;
import com.managersystem.admin.server.entities.RewardStateLog;
import com.managersystem.admin.server.stores.base.BaseStorage;
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
