package com.wiinvent.gami.domain.repositories.gvc;

import com.wiinvent.gami.domain.entities.gvc.GvcPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GvcPackageRepository extends JpaRepository<GvcPackage, Integer> {
}