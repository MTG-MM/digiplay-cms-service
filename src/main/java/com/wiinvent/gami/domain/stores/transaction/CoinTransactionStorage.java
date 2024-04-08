package com.wiinvent.gami.domain.stores.transaction;

import com.wiinvent.gami.domain.entities.transaction.CoinTransaction;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CoinTransactionStorage extends BaseStorage {
  public List<CoinTransaction> findAll(UUID userId, Long next, Long pre, int limit) {
    return coinTransactionRepository.findAll(coinTransactionCondition(userId, next, pre, limit));
  }

  public Specification<CoinTransaction> coinTransactionCondition(UUID userId, Long next, Long pre, int limit) {
    return (coinTransaction, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      if (userId != null) {
        conditionsList.add(criteriaBuilder.equal(coinTransaction.get("userId"), userId));
      }

      if (next != null && pre == null) {
        conditionsList.add(criteriaBuilder.lessThanOrEqualTo(coinTransaction.get("createdAt"), next));
      } else if (pre != null && next == null) {
        conditionsList.add(criteriaBuilder.greaterThanOrEqualTo(coinTransaction.get("createdAt"), pre));
      } else {
        conditionsList.add(criteriaBuilder.between(coinTransaction.get("createdAt"), pre, next));
      }

      query.orderBy(criteriaBuilder.desc(coinTransaction.get("createdAt")));
      CriteriaQuery<CoinTransaction> typedCriteriaQuery = criteriaBuilder.createQuery(CoinTransaction.class);
      typedCriteriaQuery.select(typedCriteriaQuery.from(CoinTransaction.class)).where(query.getRestriction());
      TypedQuery<CoinTransaction> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }
}

