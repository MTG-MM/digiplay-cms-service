package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.game.GamePackage;
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

  public Page<Game> findAll(Integer id, String name, GameStatus status, Boolean isHot, Boolean isNew, Boolean isUpdate, Boolean isLock, Integer gameCategoryId, Integer gameTypeId, Pageable pageable) {
    return gameRepository.findAll(specificationGame(id, name, status, isHot, isNew, isUpdate, isLock, gameCategoryId, gameTypeId), pageable);
  }

  public List<Game> findAllByCategoryId(Integer categoryId, List<GameStatus> statuses){
    return gameRepository.findAllByCategoryIdAndStatusIn(categoryId, statuses);
  }

  public List<Game> findAllByStatus(GameStatus status){
    return gameRepository.findAllByStatus(status);
  }

  private Specification<Game> specificationGame(Integer id, String name, GameStatus status, Boolean isHot, Boolean isNew, Boolean isUpdate, Boolean isLock, Integer gameCategoryId, Integer gameTypeId) {
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
      if(Objects.nonNull(isNew)) predicates.add(criteriaBuilder.equal(root.get("isNew"), isNew));
      if(Objects.nonNull(isUpdate)) predicates.add(criteriaBuilder.equal(root.get("isUpdate"), isUpdate));
      if(Objects.nonNull(isLock)) predicates.add(criteriaBuilder.equal(root.get("isLock"), isLock));
      if(Objects.nonNull(gameCategoryId)) predicates.add(criteriaBuilder.equal(root.get("categoryId"), gameCategoryId));
      if(Objects.nonNull(gameTypeId)) predicates.add(criteriaBuilder.equal(root.get("gameTypeId"), gameTypeId));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public void save(Game game) {
    gameRepository.save(game);
    remoteCache.del(genCacheKeys(game));
  }
  public List<String> genCacheKeys(Game game){
    List<String> cacheKeys = new ArrayList<>();
    cacheKeys.add(cacheKey.getGameById(game.getId()));
    cacheKeys.add(cacheKey.getGamesByIdIn(List.of(game.getId())));
    cacheKeys.add(cacheKey.getGamesInIds());
    cacheKeys.add(cacheKey.getGameByCategoryId(game.getCategoryId(),0));
    cacheKeys.add(cacheKey.getGameByCategoryId(game.getCategoryId(),1));
    cacheKeys.add(cacheKey.getGameByCategoryId(game.getCategoryId(),2));
    cacheKeys.add(cacheKey.getGameByCategoryId(game.getCategoryId(),3));
    cacheKeys.add(cacheKey.getGameByCategoryId(game.getCategoryId(),4));
    if (game.getGameTypeId() != null) {
      game.getGameTypeId().forEach(typeId -> {
        cacheKeys.add(cacheKey.getTopChartGame(typeId, 0));
        cacheKeys.add(cacheKey.getTopChartGame(typeId, 1));
        cacheKeys.add(cacheKey.getTopChartGame(typeId, 2));
        cacheKeys.add(cacheKey.getTopChartGame(typeId, 3));
        cacheKeys.add(cacheKey.getTopChartGame(typeId, 4));
      });
    }    return cacheKeys;
  }


  public void saveAll(List<Game> games){
    gameRepository.saveAll(games);
    games.forEach(g -> remoteCache.del(genCacheKeys(g)));
  }
}
