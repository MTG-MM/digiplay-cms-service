package com.wiinvent.gami.domain.repositories.game;

import com.wiinvent.gami.domain.entities.game.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, Integer>, JpaSpecificationExecutor<GameType> {
}