package com.wiinvent.gami.domain.stores.transaction;

import com.wiinvent.gami.domain.entities.transaction.CharacterUser;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CharacterUserStorage extends BaseStorage {
  public Page<CharacterUser> findAll(UUID userId, UUID transId, Long startDate, Long endDate, Pageable pageable) {
    return characterUserRepository.findAll(characterUserSpecification(userId, transId, startDate, endDate), pageable);
  }

  public Specification<CharacterUser> characterUserSpecification(UUID userId, UUID transId, Long startDate, Long endDate) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.equal(root.get("userId"), userId));
      if (transId != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("id"), transId));
      }
      if (startDate != null && endDate != null) {
        conditionLists.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate),
            criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate)));
      }
      query.where(criteriaBuilder.and(conditionLists.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.desc(root.get("createdAt")));
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }
}
