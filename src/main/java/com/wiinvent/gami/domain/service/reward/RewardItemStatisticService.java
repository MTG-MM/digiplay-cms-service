package com.wiinvent.gami.domain.service.reward;

import com.wiinvent.gami.domain.entities.reward.*;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.response.ListRwItemStatisticResponse;
import com.wiinvent.gami.domain.response.ListRwSegmentStatisticResponse;
import com.wiinvent.gami.domain.response.StatisticResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RewardItemStatisticService extends BaseService {

  public void processStatisticToday() {
    LocalDate nowDateAtVn = DateUtils.getNowDateAtVn();
    List<RewardSegment> rewardSegments = rewardSegmentStorage.findAll();
    rewardSegments.forEach(rewardSegment -> processStatisticInRewardSegment(rewardSegment.getId(), nowDateAtVn));
  }

  private void processStatisticInRewardSegment(Long rewardSegmentId, LocalDate nowDateAtVn) {
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findByRewardSegmentId(rewardSegmentId);
    List<RewardItem> rewardItems = rewardItemStorage.findRewardItemByIdIn(rewardSegmentDetails.stream().map(RewardSegmentDetail::getRewardItemId).toList());
    Map<Long, RewardItem> rewardItemMap = rewardItems.stream().collect(Collectors.toMap(RewardItem::getId, item -> item));
    rewardSegmentDetails.forEach(rewardSegmentDetail -> processStatisticInRewardSegmentDetail(rewardSegmentDetail, rewardItemMap, nowDateAtVn));
  }

  private void processStatisticInRewardSegmentDetail(RewardSegmentDetail segmentDetail, Map<Long, RewardItem> rewardItemMap, LocalDate nowDateAtVn) {
    long startDateAtVn = DateUtils.getStartOfDay(nowDateAtVn);
    long endDateAtVn = DateUtils.getEndOfDay(nowDateAtVn);
    RewardItemStatistic rewardItemStatistic = rewardItemStatisticStorage.findByDateAndRewardSegmentIdAndRewardItemId(nowDateAtVn, segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId());
    if (rewardItemStatistic == null) {
      rewardItemStatistic = new RewardItemStatistic();
      rewardItemStatistic.setDate(nowDateAtVn);
      rewardItemStatistic.setRewardItemId(segmentDetail.getRewardItemId());
      rewardItemStatistic.setRewardSegmentId(segmentDetail.getRewardSegmentId());
      rewardItemStatistic.setTotalRewardRemain(0);
      rewardItemStatistic.setTotalRewardReceived(0);
      rewardItemStatistic.setTotalUser(0);
    }

    Integer totalUser = rewardItemHistoryStorage.countUsersInCreatedAtBetween(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId(), startDateAtVn, endDateAtVn);
    Integer totalReceived = rewardItemHistoryStorage.countRewardItemReceivedInCreatedAtBetween(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId(), startDateAtVn, endDateAtVn);
    totalUser = totalUser == null ? 0 : totalUser;
    totalReceived = totalReceived == null ? 0 : totalReceived;
    int totalRemain = 0;
    RewardItem rewardItem = rewardItemMap.get(segmentDetail.getRewardItemId());
    RewardType rewardType = rewardTypeStorage.findById(rewardItem.getRewardTypeId());
    if (Boolean.TRUE.equals(rewardItem.getIsLimited())) {
      switch (rewardType.getType()) {
        case RewardItemType.POINT -> {
        }
        case RewardItemType.VOUCHER -> {
          Integer totalVoucherInPoll = voucherDetailStorage.getListInPollVoucherInGivenInPool(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId(), startDateAtVn, endDateAtVn);
          totalVoucherInPoll = totalVoucherInPoll == null ? 0 : totalVoucherInPoll;
          totalRemain = Math.max(totalVoucherInPoll - totalReceived, 0);
        }
        case RewardItemType.PRODUCT -> {
          Integer totalProductInPoll = productDetailStorage.getListInPollProductInGivenInPool(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId(), startDateAtVn, endDateAtVn);
          totalProductInPoll = totalProductInPoll == null ? 0 : totalProductInPoll;
          totalRemain = Math.max(totalProductInPoll - totalReceived, 0);
        }
        default -> {

        }
      }

    }

    rewardItemStatistic.setTotalUser(totalUser);
    rewardItemStatistic.setTotalRewardReceived(totalReceived);
    rewardItemStatistic.setTotalRewardRemain(totalRemain);
    rewardItemStatisticStorage.save(rewardItemStatistic);
  }

  public List<ListRwSegmentStatisticResponse> statisticTotal(String startDate, String endDate) {
    LocalDate start = DateUtils.convertStringToLocalDate(startDate);
    LocalDate end = DateUtils.convertStringToLocalDate(endDate);
    List<ListRwSegmentStatisticResponse> rwSegmentStatisticResponses = new ArrayList<>();

    List<RewardItemStatistic> rewardItemStatistics = rewardItemStatisticStorage.findByGteAndLte(start, end);

    Map<Long, List<RewardItemStatistic>> groupRwSegmentMap = rewardItemStatistics.stream().collect
        (Collectors.groupingBy(RewardItemStatistic::getRewardSegmentId));

    for (Map.Entry<Long, List<RewardItemStatistic>> segmentEntry : groupRwSegmentMap.entrySet()) {
      ListRwSegmentStatisticResponse statisticResponse = new ListRwSegmentStatisticResponse();
      statisticResponse.setRwSegmentId(segmentEntry.getKey());
      statisticResponse.setRwItemList(getRewardItemStatistic(segmentEntry.getKey(), start, end));
      rwSegmentStatisticResponses.add(statisticResponse);
    }
    return rwSegmentStatisticResponses;
  }

  public List<ListRwItemStatisticResponse> getRewardItemStatistic(Long rewardSegmentId, LocalDate startDate, LocalDate endDate) {
    List<ListRwItemStatisticResponse> dataResponse = new ArrayList<>();
    List<RewardItemStatistic> rewardItemStatistics = rewardItemStatisticStorage.findByRewardSegmentIdAndDateBetween(rewardSegmentId, startDate, endDate);
    Map<Long, List<RewardItemStatistic>> mapRwItemId = rewardItemStatistics.stream().collect(Collectors.groupingBy(RewardItemStatistic::getRewardItemId));
    for (Map.Entry<Long, List<RewardItemStatistic>> itemStatistic : mapRwItemId.entrySet()) {
      List<StatisticResponse> statisticResponses = new ArrayList<>();
      Map<LocalDate, RewardItemStatistic> localDateRewardItemStatisticMap = itemStatistic.getValue()
          .stream().collect(Collectors.toMap(RewardItemStatistic::getDate, Function.identity()));

      ListRwItemStatisticResponse listRewardItemStatisticResponse = new ListRwItemStatisticResponse();
      listRewardItemStatisticResponse.setRewardItemId(itemStatistic.getKey());

      List<LocalDate> localDates = DateUtils.getDatesBetween(startDate, endDate);
      for (LocalDate current : localDates) {
        RewardItemStatistic rewardItemStatistic = localDateRewardItemStatisticMap.get(current);

        if (rewardItemStatistic == null) {
          rewardItemStatistic = new RewardItemStatistic();
          rewardItemStatistic.setRewardItemId(mapRwItemId.get(itemStatistic.getKey()).getFirst().getRewardItemId());
          rewardItemStatistic.setDate(current);
        }
        StatisticResponse statisticResponse = rewardItemStatistic.convertToStatisticResponse();
        statisticResponses.add(statisticResponse);
      }
      List<Long> rwItemIds = statisticResponses.stream().map(StatisticResponse::getRewardItemId).toList();
      Map<Long, RewardItem> rewardItemMap = rewardItemStorage.findRewardItemByIdIn(rwItemIds).stream()
          .collect(Collectors.toMap(RewardItem::getId, Function.identity()));
      for (StatisticResponse statisticResponse : statisticResponses) {
        RewardItem rewardItem = rewardItemMap.get(statisticResponse.getRewardItemId());
        statisticResponse.setRewardItemName(rewardItem != null ? rewardItem.getRewardName() : null);
      }
      listRewardItemStatisticResponse.setStatisticResponseList(statisticResponses);
      dataResponse.add(listRewardItemStatisticResponse);
    }
    return dataResponse;
  }
}
