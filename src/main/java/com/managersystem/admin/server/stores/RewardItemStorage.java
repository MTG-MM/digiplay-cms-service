package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardItemStorage extends BaseStorage {

  public List<RewardItem> findAll() {
    return rewardItemRepository.findAll();
  }

  public void save(RewardItem rewardItem) {
    rewardItemRepository.save(rewardItem);
  }
}
