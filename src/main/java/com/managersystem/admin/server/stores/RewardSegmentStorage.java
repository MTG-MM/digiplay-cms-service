package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardSegment;
import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.stores.base.BaseStorage;
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
