package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.game.GameTournament;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameTournamentStorage extends BaseStorage{
  public GameTournament findById(String id){
    return gameTournamentRepository.findById(id).orElse(null);
  }
  public void save(GameTournament gameTournament){
    gameTournamentRepository.save(gameTournament);
    remoteCache.del(genCacheKeys(gameTournament));
  }

  public GameTournament findByIdAndStatusNot(String id, Status status) {
    return gameTournamentRepository.findByIdAndStatusNot(id, status);
  }

  public Page<GameTournament> findAll(Integer gameId, Status status, Pageable pageable) {
    return gameTournamentRepository.findAll(gameTournamentCondition(gameId, status), pageable);
  }

  public Specification<GameTournament> gameTournamentCondition(Integer gameId, Status status){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (gameId != null){
        conditionList.add(criteriaBuilder.equal(root.get("gameId"), gameId));
      }
      if (status != null){
        conditionList.add(criteriaBuilder.equal(root.get("status"), status));
      }

      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }

  public List<String> genCacheKeys(GameTournament gameTournament){
    List<String> keys = new ArrayList<>();
    keys.add(cacheKey.genGameTournamentById(gameTournament.getId()));
    return keys;
  }
}
