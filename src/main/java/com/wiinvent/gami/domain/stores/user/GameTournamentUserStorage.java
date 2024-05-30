package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.response.GameTournamentUserResponse;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GameTournamentUserStorage extends BaseStorage {
  public Page<GameTournamentUserResponse> getAllOrderByCoin(String id, String name, Pageable pageable){
    return gameTournamentUserRepository.getAllOrderByCoin(id, name , pageable);
  }

  public Page<GameTournamentUserResponse> getAllOrderByPoint(String id, String name, Pageable pageable){
    return gameTournamentUserRepository.getAllOrderByPoint(id, name, pageable);
  }
}
