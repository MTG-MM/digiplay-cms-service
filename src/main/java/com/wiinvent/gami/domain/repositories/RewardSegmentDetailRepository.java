package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.RewardSegmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardSegmentDetailRepository extends JpaRepository<RewardSegmentDetail, Long>,
    JpaSpecificationExecutor<RewardSegmentDetail> {
  List<RewardSegmentDetail> findByRewardSegmentId(Long rewardSegmentId);

  List<RewardSegmentDetail> findByRewardSegmentIdAndRewardItemIdIn(Long rwSegmentId, List<Long> ids);

  RewardSegmentDetail findByRewardSegmentIdAndRewardItemId(Long rwSegment, Long rwItemId);
}
