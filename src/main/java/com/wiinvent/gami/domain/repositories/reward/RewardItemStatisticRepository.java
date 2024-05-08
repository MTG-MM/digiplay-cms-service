package com.wiinvent.gami.domain.repositories.reward;

import com.wiinvent.gami.domain.entities.reward.RewardItemStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RewardItemStatisticRepository extends JpaRepository<RewardItemStatistic, Long> {
  RewardItemStatistic findByDateAndRewardSegmentIdAndRewardItemId(LocalDate nowDateAtVn, Long rewardSegmentId, Long rewardItemId);

  List<RewardItemStatistic> findByRewardSegmentIdAndDateBetween(Long rewardSegmentId, LocalDate startDate, LocalDate endDate);

  List<RewardItemStatistic> findByDateGreaterThanEqualAndDateLessThanEqual(LocalDate gte, LocalDate lte);
}
