package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.RewardSchedule;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardScheduleStorage extends BaseStorage {

  public List<RewardSchedule> findByRewardSegmentDetailIdIn(List<Long> rewardSegmentIds) {
    return rewardScheduleRepository.findByRewardSegmentDetailIdIn(rewardSegmentIds);
  }

  public void save(RewardSchedule rewardSchedule) {
    rewardScheduleRepository.save(rewardSchedule);
  }

  public RewardSchedule findById(Long id) {
    return rewardScheduleRepository.findById(id).orElse(null);
  }

  public List<RewardSchedule> findAll() {
    return rewardScheduleRepository.findAll();
  }

  public List<RewardSchedule> findByRewardSegmentDetailId(Long rewardSegmentId) {
    return rewardScheduleRepository.findByRewardSegmentDetailId(rewardSegmentId);
  }
}
