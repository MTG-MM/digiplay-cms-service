package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardItemStore;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardItemStoreStorage extends BaseStorage {

  public List<RewardItemStore> findAll() {
    return rewardItemStoreRepository.findAll();
  }

  public void save(RewardItemStore rewardItemStore) {
    rewardItemStoreRepository.save(rewardItemStore);
  }

  public Page<RewardItemStore> findAll(Pageable pageable) {
    return rewardItemStoreRepository.findAll(pageable);
  }

  public RewardItemStore findById(Long id) {
    return rewardItemStoreRepository.findById(id).orElse(null);
  }
}
