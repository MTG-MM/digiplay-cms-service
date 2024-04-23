package com.wiinvent.gami.domain.stores.transaction;

import com.wiinvent.gami.domain.entities.transaction.CharacterUserTransaction;
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
public class CharacterUserTransactionStorage extends BaseStorage {
  public List<CharacterUserTransaction> findAll(UUID userId, Long next, Long pre, int limit, CursorType type) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<CharacterUserTransaction> query = criteriaBuilder.createQuery(CharacterUserTransaction.class);
    Root<CharacterUserTransaction> root = query.from(CharacterUserTransaction.class);
    List<Predicate> conditionLists = new ArrayList<>();
    conditionLists.add(criteriaBuilder.equal(root.get("userId"), userId));
    conditionLists.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("createdAt"), pre),
        criteriaBuilder.lessThan(root.get("createdAt"), next)));
    if (type == CursorType.FIRST || type == CursorType.NEXT){
      query.where(criteriaBuilder.and(conditionLists.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.desc(root.get("createdAt")));
    }else if (type == CursorType.PRE){
      query.where(criteriaBuilder.and(conditionLists.toArray(new Predicate[0])))
      .orderBy(criteriaBuilder.asc(root.get("createdAt")));
    }
    TypedQuery<CharacterUserTransaction> typedQuery = entityManager.createQuery(query);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }
}
