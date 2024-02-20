package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardItemRepository extends JpaRepository<RewardItem, Long> {


  List<RewardItem> findByIdIn(List<Long> ids);
}
