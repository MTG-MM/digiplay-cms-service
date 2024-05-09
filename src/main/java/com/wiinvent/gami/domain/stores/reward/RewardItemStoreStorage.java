package com.wiinvent.gami.domain.stores.reward;

import com.wiinvent.gami.domain.entities.reward.RewardItemStore;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RewardItemStoreStorage extends BaseStorage {

  public List<RewardItemStore> findAll() {
    return rewardItemStoreRepository.findAll();
  }

  public void save(RewardItemStore rewardItemStore) {
    rewardItemStoreRepository.save(rewardItemStore);
    removeCache(rewardItemStore);
  }

  public void saveAll(List<RewardItemStore> rewardItemStores) {
    rewardItemStoreRepository.saveAll(rewardItemStores);
    rewardItemStores.forEach(this::removeCache);
  }

  public Page<RewardItemStore> findAll(StoreType type, String name, Pageable pageable) {
    return rewardItemStoreRepository.findAll(itemStoreSpecification(type, name), pageable);
  }

  public Specification<RewardItemStore> itemStoreSpecification(StoreType type, String name) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (type != null) {
        conditionList.add(criteriaBuilder.equal(root.get("type"), type));
      }
      if (name != null) {
        conditionList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }

  public RewardItemStore findById(Long id) {
    return rewardItemStoreRepository.findById(id).orElse(null);
  }

  public List<RewardItemStore> findByType(StoreType type) {
    return rewardItemStoreRepository.findByType(type);
  }

  public void removeCache(RewardItemStore rewardItemStore) {
    remoteCache.del(cacheKey.genRewardItemStoreById(rewardItemStore.getId()));
  }

}
