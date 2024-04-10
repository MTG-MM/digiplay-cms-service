package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.type.GameStatus;
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
public class GameStorage extends BaseStorage {


  public Game findById(Integer id) {
    return gameRepository.findGameByIdAndStatusIn(id, Game.getListStatusShow());
  }

  public Page<Game> findAll(Integer id, String name, GameStatus status, Boolean isHot, Integer gameCategoryId, Integer gameTypeId, Pageable pageable) {
    return gameRepository.findAll(specificationGame(id, name, status, isHot, gameCategoryId, gameTypeId), pageable);
  }

  public List<Game> findAllByCategoryId(Integer categoryId, List<GameStatus> statuses){
    return gameRepository.findAllByCategoryIdAndStatusIn(categoryId, statuses);
  }

  private Specification<Game> specificationGame(Integer id, String name, GameStatus status, Boolean isHot, Integer gameCategoryId, Integer gameTypeId) {
    return (Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (id != null) {
        predicates.add(criteriaBuilder.equal(root.get("id"), id));
      }
      if (name != null) {
        predicates.add(criteriaBuilder.equal(root.get("name"), name));
      }

      if(Objects.nonNull(status)) predicates.add(criteriaBuilder.equal(root.get("status"), status));
      else predicates.add(criteriaBuilder.in(root.get("status")).value(Game.getListStatusShow()));

      if(Objects.nonNull(isHot)) predicates.add(criteriaBuilder.equal(root.get("isHot"), isHot));
      if(Objects.nonNull(isHot)) predicates.add(criteriaBuilder.equal(root.get("categoryId"), gameCategoryId));
      if(Objects.nonNull(isHot)) predicates.add(criteriaBuilder.equal(root.get("gameTypeId"), gameTypeId));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public void save(Game game) {
    gameRepository.save(game);
  }

  public void saveAll(List<Game> games){
    gameRepository.saveAll(games);
  }
}
