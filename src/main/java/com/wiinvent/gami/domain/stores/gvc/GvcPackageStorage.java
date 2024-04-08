package com.wiinvent.gami.domain.stores.gvc;

import com.wiinvent.gami.domain.entities.gvc.GvcPackage;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GvcPackageStorage extends BaseStorage {
  public void save(GvcPackage gvcPackage){
    gvcPackageRepository.save(gvcPackage);
  }

  public GvcPackage findById(Integer id){
    return gvcPackageRepository.findGvcPackageByIdAndStatusIn(id, GvcPackage.getListStatusShow());
  }

  public Page<GvcPackage> findAll(Pageable pageable){
    return gvcPackageRepository.findAllByStatusIn(GvcPackage.getListStatusShow(), pageable);
  }
}
