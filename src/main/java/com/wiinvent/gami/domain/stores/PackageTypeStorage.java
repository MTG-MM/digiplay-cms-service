package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.PackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PackageTypeStorage extends BaseStorage{
  public Page<PackageType> findAll(Pageable pageable){
    return packageTypeRepository.findAllByStatusIn(PackageType.getListStatusShow(), pageable);
  }

  public PackageType findById(Integer id){
    return packageTypeRepository.findPackageTypeByIdAndStatusIn(id, PackageType.getListStatusShow());
  }

  public PackageType save(PackageType packageType){
    return packageTypeRepository.save(packageType);
  }

  public List<PackageType> findAllPackageTypeActive(){
    return packageTypeRepository.findAllByStatusIn(List.of(Status.ACTIVE));
  }

}
