package com.wiinvent.gami.domain.stores.reward;

import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.reward.RewardSegment;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
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
public class RewardItemStorage extends BaseStorage {

  public List<RewardItem> findAll() {
    return rewardItemRepository.findAll();
  }

  public void save(RewardItem rewardItem) {
    rewardItemRepository.save(rewardItem);
    remoteCache.del(getRemoveKeys(rewardItem));
  }

  public Page<RewardItem> findAll(Pageable pageable) {
    return rewardItemRepository.findAll(pageable);
  }

  public Page<RewardItem> findAll(Specification<RewardItem> rwItemCondition, Pageable pageable) {
    return rewardItemRepository.findAll(rwItemCondition, pageable);
  }

  public List<RewardItem> findAllListRwItem(String name, RewardItemType type) {
    return rewardItemRepository.findAll(rwItemCondition(name, type));
  }

  public Specification<RewardItem> rwItemCondition(String name, RewardItemType type) {
    return (rwItem, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();
      conditionsList.add(criteriaBuilder.equal(rwItem.get("status"), Status.ACTIVE));
      if (name != null) {
        conditionsList.add(criteriaBuilder.like(rwItem.get("rewardName"), "%" + name + "%"));
      }
      if (type != null) {
        conditionsList.add(criteriaBuilder.equal(rwItem.get("rewardType"), type));
      }
      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }

  List<String> getRemoveKeys(RewardItem rewardItem) {
    List<String> removeKeys = new ArrayList<>();
    for (ResourceType type : ResourceType.values()) {
      switch (type) {
        case SPIN -> {
          List<RewardSegment> rewardSegments = rewardSegmentRepository.findAll();
          rewardSegments.forEach(rewardSegment ->
              removeKeys.add(cacheKey.genListRewardItemByTypeAndId(type, rewardSegment.getId())));
        }
//        case JACKPOT_REWARD -> {
//          List<JackpotGame> jackpotGames = jackpotGameRepository.findAll();
//          jackpotGames.forEach(jackpotGame ->
//              removeKeys.add(cacheKey.genListRewardItemByTypeAndId(type, jackpotGame.getId())));
//        }
        default -> {

        }
      }
    }
    removeKeys.add(cacheKey.genAllRewardItem());
    removeKeys.add(cacheKey.gemRewardItemById(rewardItem.getId()));
    return removeKeys;
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

  public List<RewardItem> findRewardItemByStatus() {
    return rewardItemRepository.findRewardItemByStatus(Status.ACTIVE);
  }
}
