package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Achievement;
import com.wiinvent.gami.domain.entities.PackageType;
import com.wiinvent.gami.domain.entities.type.AchievementType;
import com.wiinvent.gami.domain.entities.type.ProductPackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PackageTypeStorage extends BaseStorage{
  public Page<PackageType> findAll(ProductPackageType type, Pageable pageable){
    return packageTypeRepository.findAll(packageTypeSpecification(type), pageable);
  }

  public PackageType findById(Integer id){
    return packageTypeRepository.findPackageTypeByIdAndStatusIn(id, PackageType.getListStatusShow());
  }

  public void save(PackageType packageType){
    packageTypeRepository.save(packageType);
    remoteCache.del(cacheKey.genPackageTypeById(packageType.getId()));
  }

  public List<PackageType> findAllPackageTypeActive(){
    return packageTypeRepository.findAllByStatusIn(List.of(Status.ACTIVE));
  }

  private Specification<PackageType> packageTypeSpecification(ProductPackageType type) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (type != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("type"), type));
      }
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }
}
