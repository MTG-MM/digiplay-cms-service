package com.wiinvent.gami.domain.stores.reward;

import com.wiinvent.gami.domain.entities.reward.RewardSegment;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardSegmentStorage extends BaseStorage {

  public List<RewardSegment> findByStatus(Status status) {
    return rewardSegmentRepository.findByStatus(status);
  }

  public void save(RewardSegment rewardSegment) {
    rewardSegmentRepository.save(rewardSegment);
  }

  public RewardSegment findById(Long id) {
    return rewardSegmentRepository.findById(id).orElse(null);
  }

  public Page<RewardSegment> findAll(Pageable pageable) {
    return rewardSegmentRepository.findAll(pageable);
  }


  public RewardSegment findByCode(String code) {
    return rewardSegmentRepository.findByCode(code);
  }

  public List<RewardSegment> findAll() {
    return rewardSegmentRepository.findAll();
  }
}
