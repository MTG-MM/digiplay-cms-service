package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import org.springframework.stereotype.Component;

@Component
public class PackageStorage extends BaseStorage {
  public Package findById(int id) {
    return packageRepository.findById(id).orElseThrow(null);
  }

  public void save(Package aPackage) {
    packageRepository.save(aPackage);
  }

}
