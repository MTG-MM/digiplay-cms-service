package com.wiinvent.gami.domain.stores.Transaction;

import com.wiinvent.gami.domain.entities.Transaction.ExpHistory;
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
public class ExpHistoryStorage extends BaseStorage {
  public List<ExpHistory> findAll(UUID userId, Long next, Long pre, int limit){
    return expHistoryRepository.findAll(expHistoryCondition(userId, next, pre, limit));
  }

  public Specification<ExpHistory> expHistoryCondition(UUID userId, Long next, Long pre, int limit){
    return (expHistory, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      if (userId != null){
        conditionList.add(criteriaBuilder.equal(expHistory.get("userId"),userId));
      }
      if (next != null && pre == null){
        conditionList.add(criteriaBuilder.lessThanOrEqualTo(expHistory.get("createdAt"), next));
      }else if(next == null && pre != null){
        conditionList.add(criteriaBuilder.greaterThanOrEqualTo(expHistory.get("createdAt"), pre));
      }else {
        conditionList.add(criteriaBuilder.between(expHistory.get("createdAt"), pre, next));
      }
      query.orderBy(criteriaBuilder.desc(expHistory.get("createdAt")));
      CriteriaQuery<ExpHistory> typedCriteriaQuery = criteriaBuilder.createQuery(ExpHistory.class);
      typedCriteriaQuery.select(typedCriteriaQuery.from(ExpHistory.class)).where(query.getRestriction());
      TypedQuery<ExpHistory> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }
}
