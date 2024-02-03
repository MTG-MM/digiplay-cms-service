package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardSchedule;
import com.managersystem.admin.server.stores.base.BaseStorage;
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
}
