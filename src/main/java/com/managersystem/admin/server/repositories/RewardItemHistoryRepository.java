package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardItemHistoryRepository extends JpaRepository<RewardItemHistory, UUID> {


}
