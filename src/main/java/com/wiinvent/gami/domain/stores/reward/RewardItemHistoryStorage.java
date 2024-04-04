package com.wiinvent.gami.domain.stores.reward;

import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.reward.RewardItemHistory;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RewardItemHistoryStorage extends BaseStorage {

  public void save(RewardItemHistory rewardItemHistory) {
    rewardItemHistoryRepository.save(rewardItemHistory);
  }

  public Integer countUsersInCreatedAtBetween(Long rewardSegmentId, Long rewardItemId, long startDateAtVn, long endDateAtVn) {
    return rewardItemHistoryRepository.countUsersInCreatedAtBetween(rewardSegmentId, rewardItemId, startDateAtVn, endDateAtVn);
  }

  public Integer countRewardItemReceivedInCreatedAtBetween(Long rewardSegmentId, Long rewardItemId, long startDateAtVn, long endDateAtVn) {
    return rewardItemHistoryRepository.countRewardItemReceivedInCreatedAtBetween(rewardSegmentId, rewardItemId, startDateAtVn, endDateAtVn);
  }

  public List<RewardItemHistory> findAll(UUID userId, Long next, Long pre, int limit) {
    return rewardItemHistoryRepository.findAll(rewardItemHistoryCondition(userId, next, pre, limit));
  }

  public Specification<RewardItemHistory> rewardItemHistoryCondition(UUID userId, Long next, Long pre, int limit) {
    return (rwItemHistory, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      if (userId != null) {
        conditionsList.add(criteriaBuilder.equal(rwItemHistory.get("userId"), userId));
      }

      if (next != null && pre == null) {
        conditionsList.add(criteriaBuilder.lessThanOrEqualTo(rwItemHistory.get("createdAt"), next));
      } else if (pre != null && next == null) {
        conditionsList.add(criteriaBuilder.greaterThanOrEqualTo(rwItemHistory.get("createdAt"), pre));
      } else {
        conditionsList.add(criteriaBuilder.between(rwItemHistory.get("createdAt"), pre, next));
      }

      query.orderBy(criteriaBuilder.desc(rwItemHistory.get("createdAt")));
      CriteriaQuery<RewardItemHistory> typedCriteriaQuery = criteriaBuilder.createQuery(RewardItemHistory.class);

      typedCriteriaQuery.select(typedCriteriaQuery.from(RewardItemHistory.class)).where(query.getRestriction());
      TypedQuery<RewardItemHistory> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }
}
