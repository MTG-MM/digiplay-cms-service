package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.game.GameTournament;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GameTournamentStorage extends BaseStorage{
  public GameTournament findById(String id){
    return gameTournamentRepository.findById(id).orElse(null);
  }
  public void save(GameTournament gameTournament){
    gameTournamentRepository.save(gameTournament);
  }

  public GameTournament findByIdAndStatusNot(String id, Status status) {
    return gameTournamentRepository.findByIdAndStatusNot(id, status);
  }

  public Page<GameTournament> findAll(Pageable pageable) {
    return gameTournamentRepository.findAll(pageable);
  }
}
