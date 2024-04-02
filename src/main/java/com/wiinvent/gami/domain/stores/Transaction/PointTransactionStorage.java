package com.wiinvent.gami.domain.stores.Transaction;

import com.wiinvent.gami.domain.entities.Transaction.CoinTransaction;
import com.wiinvent.gami.domain.entities.Transaction.PointTransaction;
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
public class PointTransactionStorage extends BaseStorage {
  public List<PointTransaction> findAll(UUID userId, Long next, Long pre, int limit) {
    return pointTransactionRepository.findAll(pointTransactionCondition(userId, next, pre, limit));
  }

  public Specification<PointTransaction> pointTransactionCondition(UUID userId, Long next, Long pre, int limit) {
    return (pointTransaction, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      if (userId != null) {
        conditionsList.add(criteriaBuilder.equal(pointTransaction.get("userId"), userId));
      }

      if (next != null && pre == null) {
        conditionsList.add(criteriaBuilder.lessThanOrEqualTo(pointTransaction.get("createdAt"), next));
      } else if (pre != null && next == null) {
        conditionsList.add(criteriaBuilder.equal(pointTransaction.get("createdAt"), pre));
      } else {
        conditionsList.add(criteriaBuilder.between(pointTransaction.get("createdAt"), pre, next));
      }

      query.orderBy(criteriaBuilder.desc(pointTransaction.get("createdAt")));
      CriteriaQuery<PointTransaction> typedCriteriaQuery = criteriaBuilder.createQuery(PointTransaction.class);
      typedCriteriaQuery.select(typedCriteriaQuery.from(PointTransaction.class)).where(query.getRestriction());
      TypedQuery<PointTransaction> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }
}
