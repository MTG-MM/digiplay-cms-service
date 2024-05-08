package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.PackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageTypeRepository extends JpaRepository<PackageType, Integer>, JpaSpecificationExecutor<PackageType> {
  PackageType findPackageTypeByIdAndStatusIn(Integer id, List<Status> statuses);
  Page<PackageType> findAllByStatusIn(List<Status> statuses, Pageable pageable);
  List<PackageType> findAllByStatusIn(List<Status> statuses);
}