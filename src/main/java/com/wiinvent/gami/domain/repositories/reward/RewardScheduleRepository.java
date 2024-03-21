package com.wiinvent.gami.domain.repositories.reward;

import com.wiinvent.gami.domain.entities.reward.RewardSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardScheduleRepository extends JpaRepository<RewardSchedule, Long> {


  List<RewardSchedule> findByRewardSegmentDetailIdIn(List<Long> rewardSegmentIds);

  List<RewardSchedule> findByRewardSegmentDetailId(Long rewardSegmentId);

}
