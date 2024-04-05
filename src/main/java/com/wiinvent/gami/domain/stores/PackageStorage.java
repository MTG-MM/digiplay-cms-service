package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PackageStorage extends BaseStorage {
  public Package findById(int id) {
    return packageRepository.findById(id).orElseThrow(null);
  }

  public void save(Package aPackage) {
    packageRepository.save(aPackage);
  }

  public Package findByIdAndStatusNot(int id, Status status) {
    return packageRepository.findByIdAndStatusNot(id, status);
  }

  public Page<Package> findAll(Pageable pageable) {
    return packageRepository.findAll(pageable);
  }
}
