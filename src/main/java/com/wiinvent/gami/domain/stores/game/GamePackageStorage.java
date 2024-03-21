package com.wiinvent.gami.domain.stores.game;

import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

@Component
public class GamePackageStorage extends BaseStorage {


  public GamePackage findById(int id) {
    return gamePackageRepository.findById(id).orElseThrow(null);
  }

  public void save(GamePackage gamePackage) {
    gamePackageRepository.save(gamePackage);
  }
}
