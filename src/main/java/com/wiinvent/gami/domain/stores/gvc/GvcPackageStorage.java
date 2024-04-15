package com.wiinvent.gami.domain.stores.gvc;

import com.wiinvent.gami.domain.entities.gvc.GvcPackage;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GvcPackageStorage extends BaseStorage {
  public void save(GvcPackage gvcPackage){
    gvcPackageRepository.save(gvcPackage);
    remoteCache.del(genCacheKeys(gvcPackage));
  }

  public GvcPackage findById(Integer id){
    return gvcPackageRepository.findGvcPackageByIdAndStatusIn(id, GvcPackage.getListStatusShow());
  }

  public Page<GvcPackage> findAll(Pageable pageable){
    return gvcPackageRepository.findAllByStatusIn(GvcPackage.getListStatusShow(), pageable);
  }

  public List<String> genCacheKeys(GvcPackage gvcPackage){
    List<String> cacheKeys = new ArrayList<>();
    cacheKeys.add(cacheKey.getGvcPackages());
    cacheKeys.add(cacheKey.getGvcPackageByCode(gvcPackage.getCode()));
    return cacheKeys;
  }
}
