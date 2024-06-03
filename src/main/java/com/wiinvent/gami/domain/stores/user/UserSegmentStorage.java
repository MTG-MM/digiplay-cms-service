package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.Achievement;
import com.wiinvent.gami.domain.entities.type.AchievementType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.user.UserSegment;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSegmentStorage extends BaseStorage {

  public List<UserSegment> findByIsDefault(Boolean isDefault) {
    return userSegmentRepository.findByIsDefault(isDefault);
  }

  public void save(UserSegment userSegment) {
    userSegmentRepository.save(userSegment);
    remoteCache.del(genCacheKeys(userSegment));
  }

  public UserSegment findById(Long userSegmentId) {
    return userSegmentRepository.findById(userSegmentId).orElse(null);
  }

  public List<UserSegment> findByIdIn(List<Long> ids) {
    return userSegmentRepository.findByIdIn(ids);
  }

  public Page<UserSegment> findAll(String name, Status status, Pageable pageable) {
    return userSegmentRepository.findAll(userSegmentSpecification(name, status), pageable);
  }

  private Specification<UserSegment> userSegmentSpecification(String name, Status status) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (name != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("name"), "%" + name + "%"));
      }
      if (status != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("status"), status));
      }
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }

  public List<UserSegment> findAllUserSegmentActive() {
    return userSegmentRepository.findUserSegmentsByStatusIn(List.of(Status.ACTIVE));
  }

  public UserSegment findNextLevel(Integer currentLevel){
    return userSegmentRepository.findFirstByLevelGreaterThanOrderByLevelAsc(currentLevel);
  }

  public UserSegment findByLevel(Integer level){
    return userSegmentRepository.findUserSegmentByLevel(level);
  }

  public List<String> genCacheKeys(UserSegment userSegment){
    List<String> cacheKeys = new ArrayList<>();
    cacheKeys.add(cacheKey.genUserSegmentById(userSegment.getId()));
    cacheKeys.add(cacheKey.genUserSegmentDefault());
    return cacheKeys;
  }
}
