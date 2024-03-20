package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.reward.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardItemRepository extends JpaRepository<RewardItem, Long>, JpaSpecificationExecutor<RewardItem> {
  List<RewardItem> findAllByIdIn(List<Long> ids);

  List<RewardItem> findByIdIn(List<Long> ids);
}
