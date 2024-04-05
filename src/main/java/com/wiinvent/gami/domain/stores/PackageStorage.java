package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.type.GameStatus;
import com.wiinvent.gami.domain.entities.type.PackageType;
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
  public Page<Package> findAll(Integer id, PackageType type, Pageable pageable){
    return packageRepository.findAll(specificationPackage(id, type, Package.getListStatusShow()), pageable);
  }
  public Package findById(Integer id) {
    return packageRepository.findPackageByIdAndStatusIn(id, Package.getListStatusShow());
  }

  public void save(Package aPackage) {
    packageRepository.save(aPackage);
  }


  private Specification<Package> specificationPackage(Integer id, PackageType type, List<Status> statuses) {
    return (Root<Package> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (Objects.nonNull(id)) predicates.add(criteriaBuilder.equal(root.get("id"), id));
      if (Objects.nonNull(type)) predicates.add(criteriaBuilder.equal(root.get("packageType"), type));

      if (Objects.nonNull(statuses)) predicates.add(criteriaBuilder.in(root.get("status")).value(statuses));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

}
