package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardStateRepository extends JpaRepository<RewardState, Long> {


}
