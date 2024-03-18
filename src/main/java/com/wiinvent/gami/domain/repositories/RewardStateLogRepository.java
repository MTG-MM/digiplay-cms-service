package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.RewardStateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardStateLogRepository extends JpaRepository<RewardStateLog, UUID> {


}
