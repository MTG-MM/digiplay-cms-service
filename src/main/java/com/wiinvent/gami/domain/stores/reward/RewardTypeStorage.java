package com.wiinvent.gami.domain.stores.reward;

import com.wiinvent.gami.domain.entities.VoucherDetail;
import com.wiinvent.gami.domain.entities.reward.RewardType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RewardTypeStorage extends BaseStorage {

  public Page<RewardType> findAll(String name, Pageable pageable) {
    return rewardTypeRepository.findAll(rewardTypeSpecification(name), pageable);
  }

  public Specification<RewardType> rewardTypeSpecification(String name) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (name != null) {
        conditionLists.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
      }
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }

  public void save(RewardType rewardType) {
    rewardTypeRepository.save(rewardType);
    remoteCache.del(cacheKey.genRewardTypeById(rewardType.getId()));
  }

  public Page<RewardType> findAll(Pageable pageable) {
    return rewardTypeRepository.findAll(pageable);
  }

  public RewardType findById(Long id) {
    return rewardTypeRepository.findById(id).orElse(null);
  }
}
