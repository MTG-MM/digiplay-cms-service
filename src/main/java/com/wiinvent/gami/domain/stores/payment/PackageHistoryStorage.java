package com.wiinvent.gami.domain.stores.payment;

import com.wiinvent.gami.domain.entities.payment.PackageHistory;
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
public class PackageHistoryStorage extends BaseStorage {
  public List<PackageHistory> findAll(UUID userId, Long next, Long pre, int limit) {
    return packageHistoryRepository.findAll(paymentTransactionCondition(userId, next, pre, limit));
  }

  public Specification<PackageHistory> paymentTransactionCondition(UUID userId, Long next, Long pre, int limit) {
    return (packageHistory, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      if (userId != null) {
        conditionsList.add(criteriaBuilder.equal(packageHistory.get("userId"), userId));
      }

      if (next != null && pre == null) {
        conditionsList.add(criteriaBuilder.lessThanOrEqualTo(packageHistory.get("createdAt"), next));
      } else if (pre != null && next == null) {
        conditionsList.add(criteriaBuilder.greaterThanOrEqualTo(packageHistory.get("createdAt"), pre));
      } else {
        conditionsList.add(criteriaBuilder.between(packageHistory.get("createdAt"), pre, next));
      }

      query.orderBy(criteriaBuilder.desc(packageHistory.get("createdAt")));
      CriteriaQuery<PackageHistory> typedCriteriaQuery = criteriaBuilder.createQuery(PackageHistory.class);

      typedCriteriaQuery.select(typedCriteriaQuery.from(PackageHistory.class)).where(query.getRestriction());
      TypedQuery<PackageHistory> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }
}
