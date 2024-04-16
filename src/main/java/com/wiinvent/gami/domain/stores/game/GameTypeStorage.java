package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.GameType;
import com.wiinvent.gami.domain.entities.type.GameStatus;
import com.wiinvent.gami.domain.entities.type.GameTypeStatus;
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
public class GameTypeStorage extends BaseStorage {
  public GameType save(GameType gameType){
    return gameTypeRepository.save(gameType);
  }

  public GameType findById(Integer id){
    return gameTypeRepository.findById(id).orElseThrow(null);
  }

  public Page<GameType> findGameTypes(Integer id, String name, Pageable pageable){
    return gameTypeRepository.findAll(
        specification(id, name),
        pageable
    );
  }

  public List<GameType> findGameTypesActive(){
    return gameTypeRepository.findAllByStatusIn(List.of(GameTypeStatus.ACTIVE));
  }

  public void deleteById(Integer id){
    gameTypeRepository.deleteById(id);
  }


  private Specification<GameType> specification(Integer id, String name) {
    return (Root<GameType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if(Objects.nonNull(id)) predicates.add(criteriaBuilder.equal(root.get("id"), id));
      if(Objects.nonNull(name)) predicates.add(criteriaBuilder.equal(root.get("name"), name));
      predicates.add(criteriaBuilder.notEqual(root.get("status"), GameStatus.DELETE));
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}