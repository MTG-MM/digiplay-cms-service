package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardScheduleRepository extends JpaRepository<RewardSchedule, Long> {


  List<RewardSchedule> findByRewardSegmentDetailIdIn(List<Long> rewardSegmentIds);
}
