package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItemStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RewardItemStatisticRepository extends JpaRepository<RewardItemStatistic, Long> {
  RewardItemStatistic findByDateAndRewardSegmentIdAndRewardItemId(LocalDate nowDateAtVn, Long rewardSegmentId, Long rewardItemId);
}
