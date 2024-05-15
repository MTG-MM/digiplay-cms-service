package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Achievement;
import com.wiinvent.gami.domain.entities.type.AchievementType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class AchievementStorage extends BaseStorage{
  public Page<Achievement> findAll(String name, Status status, AchievementType achievementType, Pageable pageable) {
    return achievementRepository.findAll(achievementSpecification(name, status, achievementType), pageable);
  }

  public Achievement findById(Integer id) {
    return achievementRepository.findById(id).orElse(null);
  }

  public List<Achievement> findAllByIdIn(List<Integer> ids){
   return achievementRepository.findAllByIdIn(ids);
  }

  public List<Achievement> findAllByStatus() {
    return achievementRepository.findAllByStatus(Status.ACTIVE);
  }

  public void save(Achievement achievement) {
    achievementRepository.save(achievement);
    remoteCache.del(removeCacheKeys(achievement));
  }

  public List<String> removeCacheKeys(Achievement achievement) {
    List<String> keys = new ArrayList<>();
    keys.add(cacheKey.genAllAchievement());
    keys.add(cacheKey.genAchievementByType(achievement.getType()));
    return keys;
  }

  private Specification<Achievement> achievementSpecification(String name, Status status, AchievementType achievementType) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (name != null) {
        conditionLists.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
      }
      if (status != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("status"), status));
      }
      if (achievementType != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("achievementType"), achievementType));
      }
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }
}
