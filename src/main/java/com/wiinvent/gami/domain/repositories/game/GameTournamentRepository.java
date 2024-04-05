package com.wiinvent.gami.domain.repositories.game;

import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.game.GameTournament;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTournamentRepository extends JpaRepository<GameTournament, String>, JpaSpecificationExecutor<Game> {
  GameTournament findByIdAndStatusNot(String id, Status status);
}
