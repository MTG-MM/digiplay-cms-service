package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PackageStorage extends BaseStorage {
  public Page<Package> findAll(Integer id, Integer packageTypeId, Pageable pageable){
    return packageRepository.findAll(specificationPackage(id, packageTypeId, Package.getListStatusShow()), pageable);
  }
  public Package findById(Integer id) {
    return packageRepository.findPackageByIdAndStatusIn(id, Package.getListStatusShow());
  }

  public void save(Package aPackage) {
    packageRepository.save(aPackage);
    remoteCache.del(genCacheKeys(aPackage));
  }

  public List<String> genCacheKeys(Package aPackage){
    List<String> cacheKeys = new ArrayList<>();
    cacheKeys.add(cacheKey.getPackageByCode(aPackage.getCode()));
    cacheKeys.add(cacheKey.getPackageById(aPackage.getId()));
    cacheKeys.add(cacheKey.getPortalPackages(0));
    cacheKeys.add(cacheKey.getPortalPackages(1));
    cacheKeys.add(cacheKey.getPortalPackages(2));
    cacheKeys.add(cacheKey.getPortalPackages(3));
    cacheKeys.add(cacheKey.getPortalPackages(4));
    cacheKeys.add(cacheKey.getPortalPackagesByTypeId(aPackage.getPackageTypeId(),0));
    cacheKeys.add(cacheKey.getPortalPackagesByTypeId(aPackage.getPackageTypeId(),1));
    cacheKeys.add(cacheKey.getPortalPackagesByTypeId(aPackage.getPackageTypeId(),2));
    cacheKeys.add(cacheKey.getPortalPackagesByTypeId(aPackage.getPackageTypeId(),3));
    cacheKeys.add(cacheKey.getPortalPackagesByTypeId(aPackage.getPackageTypeId(),4));
    return cacheKeys;
  }


  private Specification<Package> specificationPackage(Integer id, Integer packageTypeId, List<Status> statuses) {
    return (Root<Package> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (Objects.nonNull(id)) predicates.add(criteriaBuilder.equal(root.get("id"), id));
      if (Objects.nonNull(packageTypeId)) predicates.add(criteriaBuilder.equal(root.get("packageTypeId"), packageTypeId));

      if (Objects.nonNull(statuses)) predicates.add(criteriaBuilder.in(root.get("status")).value(statuses));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

}
