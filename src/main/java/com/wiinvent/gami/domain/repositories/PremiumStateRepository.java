package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.SubState;
import com.wiinvent.gami.domain.entities.type.PackageStateType;
import com.wiinvent.gami.domain.entities.user.PremiumState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PremiumStateRepository extends JpaRepository<PremiumState, UUID> {
  List<PremiumState> findByPremiumStateAndUserIdInAndEndAtGreaterThan(PackageStateType type, List<UUID> userId, Long endAtNow);
}
