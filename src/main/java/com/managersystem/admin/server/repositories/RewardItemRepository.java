package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardItemRepository extends JpaRepository<RewardItem, Long> {


}
