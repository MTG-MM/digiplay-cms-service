package com.wiinvent.gami.domain.repositories.game;

import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.type.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>, JpaSpecificationExecutor<Game> {
  Game findGameByIdAndStatusIn(Integer id, List<GameStatus> statuses);
  List<Game> findAllByCategoryIdAndStatusIn(Integer categoryId, List<GameStatus> statuses);

  List<Game> findAllByStatus(GameStatus status);
}