package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.entities.RewardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardTypeRepository extends JpaRepository<RewardType, Long> {


}
