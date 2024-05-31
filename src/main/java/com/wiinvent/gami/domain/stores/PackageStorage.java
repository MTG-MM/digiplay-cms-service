package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.type.ProductPackageType;
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
  public Page<Package> findAll(Integer id, Integer packageTypeId, Pageable pageable) {
    return packageRepository.findAll(specificationPackage(id, packageTypeId, Package.getListStatusShow()), pageable);
  }

  public Package findById(Integer id) {
    return packageRepository.findPackageByIdAndStatusIn(id, Package.getListStatusShow());
  }

  public void save(Package productPackage) {
    packageRepository.save(productPackage);
    remoteCache.del(removeCache(productPackage));
  }

  private List<String> removeCache(Package portalPackage) {
    List<String> removeCaches = new ArrayList<>();
    for(int page = 0; page < 100 ; page++) {
      removeCaches.add(cacheKey.getPortalPackagesByTypeId(portalPackage.getPackageTypeId(), page));
      removeCaches.add(cacheKey.getPortalPackages(page));
    }
    removeCaches.add(cacheKey.getPackageById(portalPackage.getId()));
    removeCaches.add(cacheKey.getPackageByCode(portalPackage.getCode()));
    return removeCaches;
  }

  public List<Package> findAllPackageActive(Integer typeId) {
    return packageRepository.findAll(packageActiveCondition(typeId));
  }

  public Specification<Package> packageActiveCondition(Integer typeId) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (typeId != null) {
        conditionList.add(criteriaBuilder.equal(root.get("packageTypeId"), typeId));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }


  private Specification<Package> specificationPackage(Integer id, Integer packageTypeId, List<Status> statuses) {
    return (Root<Package> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (Objects.nonNull(id)) predicates.add(criteriaBuilder.equal(root.get("id"), id));
      if (Objects.nonNull(packageTypeId))
        predicates.add(criteriaBuilder.equal(root.get("packageTypeId"), packageTypeId));

      if (Objects.nonNull(statuses)) predicates.add(criteriaBuilder.in(root.get("status")).value(statuses));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public List<Package> findPackagesByType(ProductPackageType type) {
    return packageRepository.findPackagesByType(type);
  }

  public List<Package> findPackagesByDaySub(Integer daySub) {
    if (daySub == 1) {
      return packageRepository.findPackagesByDaySub(1);
    } else if (daySub == 7) {
      return packageRepository.findPackagesByDaySub(7);
    } else if (daySub == 30) {
      return packageRepository.findByDaySubGreaterThanEqual(30);
    } else {
      return new ArrayList<>();
    }
  }
}
