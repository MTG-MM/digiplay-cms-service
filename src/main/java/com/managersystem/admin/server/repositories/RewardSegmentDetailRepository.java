package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardSegmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardSegmentDetailRepository extends JpaRepository<RewardSegmentDetail, Long> {


  List<RewardSegmentDetail> findByRewardSegmentId(Long rewardSegmentId);

  List<RewardSegmentDetail> findByRewardSegmentIdAndRewardItemIdIn(Long rwSegmentId, List<Long> ids);
}
