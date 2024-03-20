package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

  public Page<RewardItem> findAll(Specification<RewardItem> rwItemCondition, Pageable pageable) {
    return rewardItemRepository.findAll(rwItemCondition, pageable);
  }

  public RewardItem findById(Long id) {
    return rewardItemRepository.findById(id).orElse(null);
  }

  public List<RewardItem> findByIdIn(List<Long> ids) {
    return rewardItemRepository.findByIdIn(ids);
  }

  public List<RewardItem> findRewardItemByIdIn(List<Long> ids) {
    return rewardItemRepository.findAllByIdIn(ids);
  }
}
