package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.response.UserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.stores.BaseStorage;
import com.wiinvent.gami.domain.utils.Constant;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class UserStorage extends BaseStorage {

  public User findById(UUID userId) {
    return userRepository.findById(userId).orElse(null);
  }

  public void save(User user) {
    userRepository.save(user);
  }


  public List<User> findAll(UUID userId, String phoneNumber, Long next, Long pre, int limit) {
    return userRepository.findAll(userCondition(userId, phoneNumber, next, pre, limit));
  }

  public Specification<User> userCondition(UUID userId, String phoneNumber, Long next, Long pre, int limit) {
    return (user, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      if (userId != null) {
        conditionsList.add(criteriaBuilder.equal(user.get("userId"), userId));
      }
      if (phoneNumber != null) {
        conditionsList.add(criteriaBuilder.equal(user.get("phoneNumber"), phoneNumber));
      }
      if (next != null && pre == null) {
        conditionsList.add(criteriaBuilder.lessThanOrEqualTo(user.get(Constant.CREATED_AT_VARIABLE), next));
      } else if (pre != null && next == null) {
        conditionsList.add(criteriaBuilder.equal(user.get(Constant.CREATED_AT_VARIABLE), pre));
      } else {
        conditionsList.add(criteriaBuilder.between(user.get(Constant.CREATED_AT_VARIABLE), pre, next));
      }

      if(pre != null){
        query.orderBy(criteriaBuilder.desc(user.get(Constant.CREATED_AT_VARIABLE)));
      }else{
        query.orderBy(criteriaBuilder.asc(user.get(Constant.CREATED_AT_VARIABLE)));
      }
      CriteriaQuery<User> typedCriteriaQuery = criteriaBuilder.createQuery(User.class);

      typedCriteriaQuery.select(typedCriteriaQuery.from(User.class)).where(query.getRestriction());
      TypedQuery<User> typedQuery = entityManager.createQuery(typedCriteriaQuery);
      typedQuery.setMaxResults(limit);

      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }

  public List<User> findAllUser
      (UUID userId, String phoneNumber, Long next, Long pre, Integer limit, Long startDate, Long endDate, CursorType type){
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> root = query.from(User.class);
    List<Predicate> conditionList = new ArrayList<>();
    if (userId != null){
      conditionList.add(criteriaBuilder.equal(root.get("id"), userId));
    }
    if (phoneNumber != null){
      conditionList.add(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
    }
    if (startDate != null && endDate != null){
      conditionList.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("createdAt"), startDate),
          criteriaBuilder.lessThan(root.get("createdAt"), endDate)));
    }

    conditionList.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("createdAt"), pre),
        criteriaBuilder.lessThan(root.get("createdAt"), next)));
    if (type == CursorType.NEXT || type == CursorType.FIRST){
      query.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.desc(root.get("createdAt")));
    }else if (type == CursorType.PRE){
      query.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.asc(root.get("createdAt")));
    }
    TypedQuery<User> typedQuery = entityManager.createQuery(query);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }
}
