package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.GamePackage;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import com.wiinvent.gami.domain.utils.Constant;
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
