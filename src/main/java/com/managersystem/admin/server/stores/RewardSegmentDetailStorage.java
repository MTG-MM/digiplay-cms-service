package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardSegmentDetailStorage extends BaseStorage {

  public List<RewardSegmentDetail> findByRewardSegmentId(Long rewardSegmentId) {
    return rewardSegmentDetailRepository.findByRewardSegmentId(rewardSegmentId);
  }

  public void save(RewardSegmentDetail rewardSegmentDetail) {
    rewardSegmentDetailRepository.save(rewardSegmentDetail);
  }

  public RewardSegmentDetail findById(Long id) {
    return rewardSegmentDetailRepository.findById(id).orElse(null);
  }

  public List<RewardSegmentDetail> findAll() {
    return rewardSegmentDetailRepository.findAll();
  }

  public int getQuantityInPeriodType(User user, RewardSegmentDetail segmentDetail){
    Integer quantity = remoteCache.get(cacheKey.getPeriodTypeByUser(user.getId(), segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId()), Integer.class);
    if(quantity == null){
      quantity = 0;
    }
    return quantity;
  }

  public int putQuantityInPeriodType(User user, RewardSegmentDetail segmentDetail){
    Integer quantity = remoteCache.get(cacheKey.getPeriodTypeByUser(user.getId(), segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId()), Integer.class);
    if(quantity == null){
      quantity = 0;
    }
    return quantity;
  }
}
