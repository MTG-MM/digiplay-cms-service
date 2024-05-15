package com.wiinvent.gami.domain.stores.transaction;

import com.wiinvent.gami.domain.entities.QuestUserHistory;
import com.wiinvent.gami.domain.entities.transaction.CharacterUserTransaction;
import com.wiinvent.gami.domain.entities.type.QuestStatusType;
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
public class QuestUserHistoryStorage extends BaseStorage {
  public List<QuestUserHistory> findAll(UUID userId, UUID transId, QuestStatusType questState, Long startDate, Long endDate, Long next, Long pre, int limit, CursorType type) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<QuestUserHistory> query = criteriaBuilder.createQuery(QuestUserHistory.class);
    Root<QuestUserHistory> root = query.from(QuestUserHistory.class);
    List<Predicate> conditionLists = new ArrayList<>();
    conditionLists.add(criteriaBuilder.equal(root.get("userId"), userId));
    if (transId != null) {
      conditionLists.add(criteriaBuilder.equal(root.get("id"), transId));
    }
    if (questState != null) {
      conditionLists.add(criteriaBuilder.equal(root.get("questState"), questState));
    }
    if (startDate != null & endDate != null) {
      conditionLists.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate),
          criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate)));
    }
    conditionLists.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("createdAt"), pre),
        criteriaBuilder.lessThan(root.get("createdAt"), next)));
    if (type == CursorType.FIRST || type == CursorType.NEXT){
      query.where(criteriaBuilder.and(conditionLists.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.desc(root.get("endAt")));
    }else if (type == CursorType.PRE){
      query.where(criteriaBuilder.and(conditionLists.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.asc(root.get("endAt")));
    }
    TypedQuery<QuestUserHistory> typedQuery = entityManager.createQuery(query);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }
}
