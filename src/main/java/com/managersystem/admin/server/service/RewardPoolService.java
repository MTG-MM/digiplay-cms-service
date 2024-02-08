package com.managersystem.admin.server.service;

import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.entities.type.PeriodLimitType;
import com.managersystem.admin.server.service.base.BaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class RewardPoolService extends BaseService {
  public RewardItem getRandomItemInPoolRewardItem(List<RewardSegmentDetail> segmentDetail){
    return null;
  }

  private List<RewardSegmentDetail> getValidPoolDetail(User user, List<RewardSegmentDetail> rewardSegmentDetails) {
    log.debug("============>getValidPoolDetail item pre process {}", rewardSegmentDetails);
    List<RewardSegmentDetail> rewardSegmentDetailsToRemove = new ArrayList<>();
    Long defaultRate = 0L;
    for (RewardSegmentDetail rewardSegmentDetail : rewardSegmentDetails) {
      if (!checkCapping(user, rewardSegmentDetail)) {
        log.debug("============>getValidPoolDetail caping item {} ", rewardSegmentDetail);
        rewardSegmentDetailsToRemove.add(rewardSegmentDetail);
        defaultRate += rewardSegmentDetail.getPriority();
      }
    }
    log.debug("============>getValidPoolDetail remove item {} ", rewardSegmentDetailsToRemove);

    rewardSegmentDetails.removeAll(rewardSegmentDetailsToRemove);
    for (RewardSegmentDetail rewardSegmentDetail : rewardSegmentDetails) {
      if (rewardSegmentDetail.getIsDefault()) {
        rewardSegmentDetail.setUpdatePriority(rewardSegmentDetail.getPriority() + defaultRate);
      } else {
        rewardSegmentDetail.setUpdatePriority(rewardSegmentDetail.getPriority());
      }
    }
    log.debug("============>getValidPoolDetail item after process {}", rewardSegmentDetails);
    return rewardSegmentDetails;
  }
  
  public boolean checkCapping(User user, RewardSegmentDetail rewardSegmentDetail){
    if(rewardSegmentDetail.getPeriodType() == PeriodLimitType.UNLIMITED){
      return true;
    }
    if(rewardSegmentDetail.getIsDefault()){
      return true;
    }
    switch (rewardSegmentDetail.getPeriodType()){
      case DAY -> {

      }
    }
    return true;
  }
}
