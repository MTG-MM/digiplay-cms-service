package com.wiinvent.gami.domain.stores.Transaction;

import com.wiinvent.gami.domain.entities.Transaction.TurnTransaction;
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
public class TurnTransactionStorage extends BaseStorage {
  public List<TurnTransaction> findAll(UUID userId, Long next, Long pre, int limit) {
    return turnTransactionRepository.findAll(turnTransactionCondition(userId, next, pre, limit));
  }

  public Specification<TurnTransaction> turnTransactionCondition(UUID userId, Long next, Long pre, int limit) {
    return (turnTransactions, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      if (userId != null) {
        conditionsList.add(criteriaBuilder.equal(turnTransactions.get("userId"), userId));
      }

      if (next != null && pre == null) {
        conditionsList.add(criteriaBuilder.lessThanOrEqualTo(turnTransactions.get("createdAt"), next));
      } else if (pre != null && next == null) {
        conditionsList.add(criteriaBuilder.greaterThanOrEqualTo(turnTransactions.get("createdAt"), pre));
      } else {
        conditionsList.add(criteriaBuilder.between(turnTransactions.get("createdAt"), pre, next));
      }

      query.orderBy(criteriaBuilder.desc(turnTransactions.get("createdAt")));
      CriteriaQuery<TurnTransaction> typedCriteriaQuery = criteriaBuilder.createQuery(TurnTransaction.class);
      typedCriteriaQuery.select(typedCriteriaQuery.from(TurnTransaction.class)).where(query.getRestriction());
      TypedQuery<TurnTransaction> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }
}
