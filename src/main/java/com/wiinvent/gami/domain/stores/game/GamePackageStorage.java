package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.type.GameStatus;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GamePackageStorage extends BaseStorage {
  public Page<GamePackage> findAll(Integer gameId, Integer id, Status status, Pageable pageable){
    List<Status> statuses = null;
    if(Objects.isNull(status)) statuses = GamePackage.getListStatusShow();
    else statuses = List.of(status);

    return gamePackageRepository.findAll(specificationGamePackage(gameId, id, statuses), pageable);
  }

  public GamePackage findById(Integer id){
    return gamePackageRepository.findByIdAndStatusIn(id, GamePackage.getListStatusShow());
  }
  public void save(GamePackage gamePackage) {
    gamePackageRepository.save(gamePackage);
  }

  private Specification<GamePackage> specificationGamePackage(Integer gameId, Integer id, List<Status> statuses) {
    return (Root<GamePackage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if(Objects.nonNull(gameId)) predicates.add(criteriaBuilder.equal(root.get("gameId"), gameId));

      if (Objects.nonNull(id)) predicates.add(criteriaBuilder.equal(root.get("id"), id));

      if(Objects.nonNull(statuses)) predicates.add(criteriaBuilder.in(root.get("status")).value(statuses));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
