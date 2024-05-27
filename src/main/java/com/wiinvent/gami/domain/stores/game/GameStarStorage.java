package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.GameStar;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameStarStorage extends BaseStorage {

  public List<GameStar> findByGameIdIn(List<Integer> gameIds) {
    return gameStarRepository.findByGameIdIn(gameIds);
  }

  public void saveAll(List<GameStar> gameStars) {
    gameStarRepository.saveAll(gameStars);
  }

}
