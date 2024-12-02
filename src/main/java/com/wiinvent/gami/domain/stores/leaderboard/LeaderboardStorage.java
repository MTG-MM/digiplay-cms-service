package com.wiinvent.gami.domain.stores.leaderboard;


import com.wiinvent.gami.domain.entities.ExchangeItemStore;
import com.wiinvent.gami.domain.entities.leaderboard.Leaderboard;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeaderboardStorage extends BaseStorage {
  public void save(Leaderboard leaderboard) {
    leaderboardRepository.save(leaderboard);
  }

  public Leaderboard findById(Long id) {
    return leaderboardRepository.findById(id);
  }

  public Page<Leaderboard> findAll(Long id, String name, Pageable pageable) {
    return leaderboardRepository.findAll(leaderboardSpecification(id, name), pageable);
  }

  public Specification<Leaderboard> leaderboardSpecification(Long id, String name) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (id != null) {
        conditionList.add(criteriaBuilder.equal(root.get("id"), id));
      }
      if (name != null) {
        conditionList.add(criteriaBuilder.equal(root.get("name"), name));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }
}
