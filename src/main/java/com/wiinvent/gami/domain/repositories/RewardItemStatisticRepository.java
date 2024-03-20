package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.reward.RewardItemStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RewardItemStatisticRepository extends JpaRepository<RewardItemStatistic, Long> {
  RewardItemStatistic findByDateAndRewardSegmentIdAndRewardItemId(LocalDate nowDateAtVn, Long rewardSegmentId, Long rewardItemId);
}
