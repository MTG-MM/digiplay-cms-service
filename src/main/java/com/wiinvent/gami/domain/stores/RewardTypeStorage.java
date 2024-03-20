package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.reward.RewardType;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardTypeStorage extends BaseStorage {

  public List<RewardType> findAll() {
    return rewardTypeRepository.findAll();
  }

  public void save(RewardType rewardItem) {
    rewardTypeRepository.save(rewardItem);
  }

  public Page<RewardType> findAll(Pageable pageable) {
    return rewardTypeRepository.findAll(pageable);
  }

  public RewardType findById(Long id) {
    return rewardTypeRepository.findById(id).orElse(null);
  }
}
