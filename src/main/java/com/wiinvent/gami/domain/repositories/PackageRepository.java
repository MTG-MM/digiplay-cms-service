package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.type.PackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer>, JpaSpecificationExecutor<Package> {
  Package findPackageByIdAndStatusIn(Integer id, List<Status> statuses);
}