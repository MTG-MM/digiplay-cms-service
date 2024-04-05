package com.wiinvent.gami.domain.repositories.gvc;

import com.wiinvent.gami.domain.entities.gvc.GvcPackage;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GvcPackageRepository extends JpaRepository<GvcPackage, Integer>, JpaSpecificationExecutor<GvcPackage> {
  GvcPackage findGvcPackageByIdAndStatusIn(Integer id, List<Status> statuses);

  Page<GvcPackage> findAllByStatusIn(List<Status> statuses, Pageable pageable);
}