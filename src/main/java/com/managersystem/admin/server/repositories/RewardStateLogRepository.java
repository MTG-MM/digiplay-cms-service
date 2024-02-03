package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardState;
import com.managersystem.admin.server.entities.RewardStateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardStateLogRepository extends JpaRepository<RewardStateLog, UUID> {


}
