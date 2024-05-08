package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.ExchangeItemStore;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExchangeItemStoreStorage extends BaseStorage {
  public List<ExchangeItemStore> findAll() {
    return exchangeItemStoreRepository.findAll();
  }

  public void save(ExchangeItemStore exchangeItemStore) {
    exchangeItemStoreRepository.save(exchangeItemStore);
  }

  public void saveAll(List<ExchangeItemStore> exchangeItemStores) {
    exchangeItemStoreRepository.saveAll(exchangeItemStores);
  }

  public Page<ExchangeItemStore> findAll(StoreType type, String name, Pageable pageable) {
    return exchangeItemStoreRepository.findAll(exchangeItemStoreSpecification(type, name), pageable);
  }

  public Specification<ExchangeItemStore> exchangeItemStoreSpecification(StoreType type, String name) {
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

  public ExchangeItemStore findById(Long id) {
    return exchangeItemStoreRepository.findById(id).orElse(null);
  }

//  public void removeCache(RewardItemStore rewardItemStore) {
//    remoteCache.del(cacheKey.genRewardItemStoreById(rewardItemStore.getId()));
//  }

}
