package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.response.RewardResponse;
import com.managersystem.admin.server.entities.*;
import com.managersystem.admin.server.entities.type.PeriodLimitType;
import com.managersystem.admin.server.exception.BadRequestException;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Log4j2
public class RewardPoolService extends BaseService {

  @Autowired @Lazy RewardPoolService rewardPoolService;
  @Autowired @Lazy RewardItemService rewardItemService;
  @Autowired @Lazy UserSegmentService userSegmentService;

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public RewardResponse getRewardItem(UUID userId, String code){
    RewardResponse rewardResponse;
    RewardSegmentDetail segmentDetail;
    User user = userStorage.findById(userId);
    if(user == null){
      throw new ResourceNotFoundException("User not found");
    }
    RewardSegment rewardSegment = rewardSegmentStorage.findByCode(code);
    if(rewardSegment == null){
      log.error("======>getRewardItem: not found segment");
      throw new ResourceNotFoundException(Constant.SYSTEM_MAINTAIN);
    }
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findByRewardSegmentId(rewardSegment.getId());
    List<RewardSegmentDetail> validSegmentDetails = getValidPoolDetail(user, rewardSegmentDetails, rewardSegment);
    if(validSegmentDetails.isEmpty()){
      segmentDetail = getDefaultRewardSegmentDetail(rewardSegmentDetails);
    }else{
      segmentDetail = getRandomItemInPoolRewardItem(validSegmentDetails);
    }
    rewardResponse = rewardItemService.processReturnRewardItem(user, segmentDetail);
    if(rewardResponse == null){
      segmentDetail = getDefaultRewardSegmentDetail(rewardSegmentDetails);
      rewardResponse = rewardItemService.processReturnRewardItem(user, segmentDetail);
    }

    //Neu khong tra duoc qua default bao loi
    if(rewardResponse == null){
      throw new BadRequestException(Constant.INTERNAL_SERVER_ERROR);
    }

    return rewardResponse;
  }

  public RewardSegmentDetail getDefaultRewardSegmentDetail(List<RewardSegmentDetail> rewardSegmentDetails){
    RewardSegmentDetail segmentDetail = rewardSegmentDetails.stream().filter(RewardSegmentDetail::getIsDefault).findFirst().orElse(null);
    if(segmentDetail == null){
      log.error("======>getRewardItem: not found default item");
      throw new BadRequestException(Constant.SYSTEM_MAINTAIN);
    }
    return segmentDetail;
  }
  /**
   * lay ngau nhien 1 phan qua trong danh sach dua tren ti le
   * @return qua ngau nhien
   */
  public RewardSegmentDetail getRandomItemInPoolRewardItem(List<RewardSegmentDetail> list){
    RewardSegmentDetail result = null;
    Double percentTmp = 0D;
    String keyMin = "min";
    String keyMax = "max";
    Map<Long, Map<String, Double>> rewardMap = new HashMap<>();

    for (RewardSegmentDetail item : list) {
      Map<String, Double> odds = new HashMap<>();
      odds.put(keyMin, percentTmp);
      percentTmp += item.getUpdatePriority();
      odds.put(keyMax, percentTmp);
      rewardMap.put(item.getId(), odds);
    }
    if (percentTmp == 0D) return null;
    Double index = ThreadLocalRandom.current().nextDouble(0.00D, percentTmp);
    Set<Long> ids = rewardMap.keySet();
    for (Long id : ids) {
      Map<String, Double> odds = rewardMap.get(id);
      Double min = odds.get(keyMin);
      Double max = odds.get(keyMax);
      if (min <= index && index < max) {
        result = list.stream().filter(item -> item.getId().equals(id)).toList().get(0);
        break;
      }
    }
    return result;
  }

  /**
   * chuyen doi danh sach toan bo qua trong kho qua thanh danh sach nhung qua co the nhan cua user
   * @return danh sach qua hop le
   */
  private List<RewardSegmentDetail> getValidPoolDetail(User user, List<RewardSegmentDetail> rewardSegmentDetails, RewardSegment rewardSegment) {
    log.debug("============>getValidPoolDetail item pre process {}", rewardSegmentDetails);
    List<RewardSegmentDetail> rewardSegmentDetailsToRemove = new ArrayList<>();
    Long defaultRate = 0L;
    UserSegment userSegment = userSegmentService.getUserSegment(user);

    for (RewardSegmentDetail rewardSegmentDetail : rewardSegmentDetails) {
      if (!checkCapping(user, rewardSegmentDetail, userSegment, rewardSegment)) {
        log.debug("============>getValidPoolDetail caping item {} ", rewardSegmentDetail);
        rewardSegmentDetailsToRemove.add(rewardSegmentDetail);
        if(rewardSegment.getIsAccumulativePriority()){
          defaultRate += rewardSegmentDetail.getPriority();
        }
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

  /**
   * kiem tra so lan nhan qua cua user trong 1 khoang thoi gian
   * kiem tra rank cua nguoi dung
   * @return true neu nguoi dung con co the nhan qua
   */
  public boolean checkCapping(User user, RewardSegmentDetail rewardSegmentDetail, UserSegment userSegment, RewardSegment rewardSegment){
    if(rewardSegmentDetail.getPeriodType() == PeriodLimitType.UNLIMITED){
      return true;
    }
    if(rewardSegmentDetail.getIsDefault()){
      return true;
    }
    //Neu rank nguoi dung nam trong khoang min <= rank <= max
    if(rewardSegmentDetail.getSegmentRate() >= userSegment.getMinPriority()
        && rewardSegmentDetail.getSegmentRate() <= userSegment.getMaxPriority()){
      return true;
    }
    int rewardOfNumber = rewardSegmentDetailStorage.getQuantityInPeriodType(user, rewardSegmentDetail);

    return rewardOfNumber < rewardSegmentDetail.getPeriodValue();
  }

}
