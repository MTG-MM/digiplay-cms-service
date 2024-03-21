package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.Game;
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

@Component
public class GameStorage extends BaseStorage {


  public Game findById(Integer id) {
    return gameRepository.findById(id).orElse(null);
  }

  public Page<Game> findAll(Integer id, String name, Pageable pageable) {
    return gameRepository.findAll(specificationGame(id, name), pageable);
  }

  private Specification<Game> specificationGame(Integer id, String name) {
    return (Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (id != null) {
        predicates.add(criteriaBuilder.equal(root.get("id"), id));
      }
      if (name != null) {
        predicates.add(criteriaBuilder.equal(root.get("name"), name));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public void save(Game game) {
    gameRepository.save(game);
  }
}
