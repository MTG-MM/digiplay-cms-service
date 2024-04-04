package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
  Package findByIdAndStatusNot(int id, Status status);
}