package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardItemHistory;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

@Component
public class RewardItemHistoryStorage extends BaseStorage {

  public void save(RewardItemHistory rewardItemHistory) {
    rewardItemHistoryRepository.save(rewardItemHistory);
  }
}
