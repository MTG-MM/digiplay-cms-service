package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardItemRepository extends JpaRepository<RewardItem, Long>, JpaSpecificationExecutor<RewardItem> {
  List<RewardItem> findAllByIdIn(List<Long> ids);
}
