package com.wiinvent.gami.domain.stores.payment;

import com.wiinvent.gami.domain.entities.payment.PaymentTransaction;
import com.wiinvent.gami.domain.entities.reward.RewardItemHistory;
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
public class PaymentTransactionStorage extends BaseStorage {
  public List<PaymentTransaction> findAll(UUID userId, Long next, Long pre, int limit) {
    return paymentTransactionRepository.findAll(paymentTransactionCondition(userId, next, pre, limit));
  }

  public Specification<PaymentTransaction> paymentTransactionCondition(UUID userId, Long next, Long pre, int limit) {
    return (paymentTransaction, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      if (userId != null) {
        conditionsList.add(criteriaBuilder.equal(paymentTransaction.get("userId"), userId));
      }

      if (next != null && pre == null) {
        conditionsList.add(criteriaBuilder.lessThanOrEqualTo(paymentTransaction.get("createdAt"), next));
      } else if (pre != null && next == null) {
        conditionsList.add(criteriaBuilder.equal(paymentTransaction.get("createdAt"), pre));
      } else {
        conditionsList.add(criteriaBuilder.between(paymentTransaction.get("createdAt"), pre, next));
      }

      query.orderBy(criteriaBuilder.desc(paymentTransaction.get("createdAt")));
      CriteriaQuery<PaymentTransaction> typedCriteriaQuery = criteriaBuilder.createQuery(PaymentTransaction.class);

      typedCriteriaQuery.select(typedCriteriaQuery.from(PaymentTransaction.class)).where(query.getRestriction());
      TypedQuery<PaymentTransaction> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }
}
