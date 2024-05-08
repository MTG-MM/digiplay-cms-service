package com.wiinvent.gami.domain.stores.reward;

import com.wiinvent.gami.domain.entities.reward.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RewardSegmentDetailStorage extends BaseStorage {

  public List<RewardSegmentDetail> findByRewardSegmentId(Long rewardSegmentId) {
    return rewardSegmentDetailRepository.findByRewardSegmentId(rewardSegmentId);
  }

  public void save(RewardSegmentDetail rewardSegmentDetail) {
    rewardSegmentDetailRepository.save(rewardSegmentDetail);
    remoteCache.del(removeCacheKey(rewardSegmentDetail));
  }

  public RewardSegmentDetail findById(Long id) {
    return rewardSegmentDetailRepository.findById(id).orElse(null);
  }

  public List<RewardSegmentDetail> findAll() {
    return rewardSegmentDetailRepository.findAll();
  }

  public List<RewardSegmentDetail> findAll(Specification<RewardSegmentDetail> rewardSegmentDetailSpecification) {
    return rewardSegmentDetailRepository.findAll(rewardSegmentDetailSpecification);
  }

  public int getQuantityInPeriodType(User user, RewardSegmentDetail segmentDetail){
    Integer quantity = remoteCache.get(cacheKey.getPeriodTypeByUser(user.getId(), segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId()), Integer.class);
    if(quantity == null){
      quantity = 0;
    }
    return quantity;
  }

  public int putQuantityInPeriodType(User user, RewardSegmentDetail segmentDetail, int amount){
    Integer quantity = remoteCache.get(cacheKey.getPeriodTypeByUser(user.getId(), segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId()), Integer.class);
    if(quantity == null){
      quantity = 0;
    }
    remoteCache.putExpireMillis(cacheKey.getPeriodTypeByUser(user.getId(), segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId()), amount, segmentDetail.getPeriodType().getMillis() * segmentDetail.getPeriodNumber());
    return quantity;
  }

  public RewardSegmentDetail findBySegmentIdAndRwItemId(Long rewardSegmentId, Long id) {
    return rewardSegmentDetailRepository.findByRewardSegmentIdAndRewardItemId(rewardSegmentId, id);
  }

  public void deleteList(List<RewardSegmentDetail> rewardSegmentDetails) {
    rewardSegmentDetailRepository.deleteAll(rewardSegmentDetails);
  }

  public void saveAll(List<RewardSegmentDetail> rewardSegmentDetails) {
    rewardSegmentDetailRepository.saveAll(rewardSegmentDetails);
    rewardSegmentDetails.forEach(r -> remoteCache.del(removeCacheKey(r)));
  }

  public List<RewardSegmentDetail> findByIdIn(Long rewardSegmentId, List<Long> rwItemIds) {
    return rewardSegmentDetailRepository.findByRewardSegmentIdAndRewardItemIdIn(rewardSegmentId, rwItemIds);
  }

  public void deleteAllById(List<Long> removeIds) {
    rewardSegmentDetailRepository.deleteAllById(removeIds);
  }

  public List<String> removeCacheKey(RewardSegmentDetail rewardSegmentDetail){
    List<String> removeList = new ArrayList<>();
    removeList.add(cacheKey.genListRewardSegmentDetailByRewardSegmentId(rewardSegmentDetail.getRewardSegmentId()));
    return removeList;
  }
}
