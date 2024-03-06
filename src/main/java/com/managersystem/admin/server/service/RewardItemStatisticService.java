package com.managersystem.admin.server.service;

import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.entities.RewardItemStatistic;
import com.managersystem.admin.server.entities.RewardSegment;
import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardItemStatisticService extends BaseService {

  public void processStatisticToday() {
    LocalDate nowDateAtVn = DateUtils.getNowDateAtVn();
    List<RewardSegment> rewardSegments = rewardSegmentStorage.findAll();
    rewardSegments.forEach(rewardSegment -> processStatisticInRewardSegment(rewardSegment.getId(),nowDateAtVn));
  }

  private void processStatisticInRewardSegment(Long rewardSegmentId, LocalDate nowDateAtVn) {
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findByRewardSegmentId(rewardSegmentId);
    List<RewardItem> rewardItems = rewardItemStorage.findRewardItemByIdIn(rewardSegmentDetails.stream().map(RewardSegmentDetail::getRewardItemId).toList());
    Map<Long,RewardItem> rewardItemMap = rewardItems.stream().collect(Collectors.toMap(RewardItem::getId, item -> item));
    rewardSegmentDetails.forEach(rewardSegmentDetail -> processStatisticInRewardSegmentDetail(rewardSegmentDetail,rewardItemMap, nowDateAtVn));
  }

  private void processStatisticInRewardSegmentDetail(RewardSegmentDetail segmentDetail,Map<Long,RewardItem> rewardItemMap, LocalDate nowDateAtVn) {
    long startDateAtVn = DateUtils.getStartOfDay(nowDateAtVn);
    long endDateAtVn = DateUtils.getEndOfDay(nowDateAtVn);
    RewardItemStatistic rewardItemStatistic = rewardItemStatisticStorage.findByDateAndRewardSegmentIdAndRewardItemId(nowDateAtVn, segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId());
    if(rewardItemStatistic == null) {
      rewardItemStatistic = new RewardItemStatistic();
      rewardItemStatistic.setDate(nowDateAtVn);
      rewardItemStatistic.setRewardItemId(segmentDetail.getRewardItemId());
      rewardItemStatistic.setRewardSegmentId(segmentDetail.getRewardSegmentId());
      rewardItemStatistic.setTotalRewardRemain(0);
      rewardItemStatistic.setTotalRewardReceived(0);
      rewardItemStatistic.setTotalUser(0);
    }

    Integer totalUser = rewardItemHistoryStorage.countUsersInCreatedAtBetween(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId(), startDateAtVn, endDateAtVn );
    Integer totalReceived = rewardItemHistoryStorage.countRewardItemReceivedInCreatedAtBetween(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId(), startDateAtVn, endDateAtVn );
    totalUser = totalUser == null ? 0 : totalUser;
    totalReceived = totalReceived == null ? 0 : totalReceived;
    int totalRemain = 0;
    RewardItem rewardItem = rewardItemMap.get(segmentDetail.getRewardItemId());
    if (rewardItem != null && rewardItem.getIsLimited()) {
      switch (rewardItem.getRewardType()) {
        case POINT -> {
        }
        case VOUCHER -> {
          Integer totalVoucherInPoll = voucherDetailStorage.getListInPollVoucherInGivenInPool(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId(), startDateAtVn, endDateAtVn);
          totalVoucherInPoll = totalVoucherInPoll == null ? 0 : totalVoucherInPoll;
          totalRemain = totalVoucherInPoll - totalReceived;
        }
        case PRODUCT -> {
          Integer totalProductInPoll = productDetailStorage.getListInPollProductInGivenInPool(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId(), startDateAtVn, endDateAtVn);
          totalProductInPoll = totalProductInPoll == null ? 0 : totalProductInPoll;
          totalRemain = totalProductInPoll - totalReceived;
        }
        case PHYSICAL -> {
        }
      }

    }

    rewardItemStatistic.setTotalUser(totalUser);
    rewardItemStatistic.setTotalRewardReceived(totalReceived);
    rewardItemStatistic.setTotalRewardRemain(totalRemain);
    rewardItemStatisticStorage.save(rewardItemStatistic);
  }
}
