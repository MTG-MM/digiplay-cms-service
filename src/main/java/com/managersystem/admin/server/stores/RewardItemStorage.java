package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<RewardItem> findAll(Pageable pageable) {
    return rewardItemRepository.findAll(pageable);
  }

  public RewardItem findById(Long id) {
    return rewardItemRepository.findById(id).orElse(null);
  }

  public List<RewardItem> findByIdIn(List<Long> ids) {
    return rewardItemRepository.findByIdIn(ids);
  }
}
