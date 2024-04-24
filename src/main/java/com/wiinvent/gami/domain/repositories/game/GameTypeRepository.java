package com.wiinvent.gami.domain.repositories.game;

import com.wiinvent.gami.domain.entities.game.GameType;
import com.wiinvent.gami.domain.entities.type.GameTypeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, Integer>, JpaSpecificationExecutor<GameType> {
  List<GameType> findAllByStatusIn(List<GameTypeStatus> statuses);
  List<GameType> findGameTypesByIdIn(List<Integer> ids);
}