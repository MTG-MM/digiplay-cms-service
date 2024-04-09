package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.user.GameTournamentUser;
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
public class GameTournamentUserStorage extends BaseStorage {

  public List<GameTournamentUser> findAll(UUID userId, Long next, Long pre, int limit){
    return gameTournamentUserRepository.findAll(gameTournamentUserCondition(userId, next, pre, limit));
  }

  public Specification<GameTournamentUser> gameTournamentUserCondition(UUID userId, Long next, Long pre, int limit){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();
      if (userId != null) {
        conditionsList.add(criteriaBuilder.equal(root.get("userId"), userId));
      }

      if (next != null && pre == null) {
        conditionsList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), next));
      } else if (pre != null && next == null) {
        conditionsList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), pre));
      } else {
        conditionsList.add(criteriaBuilder.between(root.get("createdAt"), pre, next));
      }

      query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
      CriteriaQuery<GameTournamentUser> typedCriteriaQuery = criteriaBuilder.createQuery(GameTournamentUser.class);
      typedCriteriaQuery.select(typedCriteriaQuery.from(GameTournamentUser.class)).where(query.getRestriction());
      TypedQuery<GameTournamentUser> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }
}
