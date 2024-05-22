package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.SubState;
import com.wiinvent.gami.domain.entities.type.PackageStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SubStateRepository extends JpaRepository<SubState, UUID> {
  List<SubState> findBySubStateAndUserIdInAndEndAtGreaterThan(PackageStateType type, List<UUID> userId, Long endAtNow);

  SubState findSubStateByPackageId(Integer id);
}
