package com.wiinvent.gami.domain.stores.transaction;

import com.wiinvent.gami.domain.entities.transaction.TurnTransaction;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Component
public class TurnTransactionStorage extends BaseStorage {
  public List<TurnTransaction> findAll(UUID userId, UUID transId, Long startDate, Long endDate, Long next, Long pre, int limit, CursorType type) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<TurnTransaction> query = criteriaBuilder.createQuery(TurnTransaction.class);
    Root<TurnTransaction> root = query.from(TurnTransaction.class);
    List<Predicate> conditionList = new ArrayList<>();
    conditionList.add(criteriaBuilder.equal(root.get("userId"), userId));
    if (transId != null) {
      conditionList.add(criteriaBuilder.equal(root.get("id"), transId));
    }
    if (startDate != null && endDate != null) {
      conditionList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate),
          criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate)));
    }
    conditionList.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("createdAt"), pre),
        criteriaBuilder.lessThan(root.get("createdAt"), next)));
    if (type == CursorType.NEXT || type == CursorType.FIRST) {
      query.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.desc(root.get("createdAt")));
    } else if (type == CursorType.PRE) {
      query.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.asc(root.get("createdAt")));
    }
    TypedQuery<TurnTransaction> typedQuery = entityManager.createQuery(query);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }
}
