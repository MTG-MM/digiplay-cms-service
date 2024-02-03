package com.managersystem.admin.server.service;

import com.managersystem.admin.server.entities.*;
import com.managersystem.admin.server.entities.type.PeriodType;
import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RewardScheduleService extends BaseService {

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void addRewardSegmentQuantity() {
    List<RewardSegment> rewardSegmentEntities = rewardSegmentStorage.findByStatus(Status.ACTIVE);
    List<RewardItem> rewardItems = rewardItemStorage.findAll();
    Map<Long, RewardItem> rewardItemMap = rewardItems.stream().collect(Collectors.toMap(RewardItem::getId, Function.identity()));
    rewardSegmentEntities.forEach(rs -> processAddRewardSegmentDetail(rs.getId(), rewardItemMap));
  }

  public void processAddRewardSegmentDetail(Long rewardSegmentId, Map<Long, RewardItem> rewardItemMap) {
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findByRewardSegmentId(rewardSegmentId);
    Map<Long, RewardSegmentDetail> rewardSegmentDetailMap = rewardSegmentDetails.stream().collect(Collectors.toMap(RewardSegmentDetail::getId, Function.identity()));

    if (rewardSegmentDetails.isEmpty()) return;
    List<Long> rewardSegmentIds = rewardSegmentDetails.stream().map(RewardSegmentDetail::getId).toList();
    List<RewardSchedule> rewardSchedules = rewardScheduleStorage.findByRewardSegmentDetailIdIn(rewardSegmentIds);
    rewardSchedules.forEach(rewardSchedule -> {
      RewardItem rewardItem = null;
      RewardSegmentDetail rewardSegmentDetail = rewardSegmentDetailMap.get(rewardSchedule.getRewardSegmentDetailId());
      if (rewardSegmentDetail != null) {
        rewardItem = rewardItemMap.get(rewardSegmentDetail.getRewardItemId());
      }
      processAddQuantity(rewardSchedule, rewardItem);
    });
  }

  public void processAddQuantity(RewardSchedule rewardSchedule, RewardItem rewardItem) {
    if (rewardItem == null || !rewardItem.getIsLimited()) {
      return;
    }

    RewardState rewardState = rewardStateStorage.findById(rewardSchedule.getId());
    if (rewardState == null) {
      rewardState = new RewardState();
      rewardState.setId(rewardSchedule.getId());
      rewardState.setCountDay(0L);
      rewardState.setCountHour(0L);
      rewardState.setCountMinute(0L);
      rewardState.setLastDay(-1);
      rewardState.setLastHour(-1);
      rewardState.setLastMinute(-1);
    }

    LocalDateTime localDateTime = DateUtils.getNowDateTimeAtVn();
    if (localDateTime.getMinute() == rewardState.getLastMinute()) {
      log.debug("====>processAddQuantity process in minute");
      return;
    }
    if (rewardSchedule.getPeriodType() == PeriodType.DAY) {
      if (localDateTime.getDayOfMonth() != rewardState.getLastDay()) {
        log.debug("====>update new day {}", localDateTime);
        rewardState.setLastDay(localDateTime.getDayOfMonth());
        rewardState.setCountDay(0L);
        rewardState.setCountHour(0L);
        rewardState.setCountMinute(0L);
      }
      int minutesToNextDay = 24 * 60 - localDateTime.getHour() * 60 - localDateTime.getMinute();
      processStateDayQuantity(rewardState, rewardSchedule.getQuantity(), minutesToNextDay);
      saveUpdateStateLog(rewardState, localDateTime);
    } else if (rewardSchedule.getPeriodType() == PeriodType.HOUR) {
      if (localDateTime.getHour() != rewardState.getLastHour()) {
        log.debug("====>update new hour {}", localDateTime);
        rewardState.setLastHour(localDateTime.getHour());
        rewardState.setCountDay(0L);
        rewardState.setCountHour(0L);
        rewardState.setCountMinute(0L);
      }
      int minutesToNextHour = 60 - localDateTime.getMinute();
      processStateHourQuantity(rewardState, rewardSchedule.getQuantity(), minutesToNextHour);
      saveUpdateStateLog(rewardState, localDateTime);
    } else if (rewardSchedule.getPeriodType() == PeriodType.MINUTE) {
      log.debug("====>update new minute {}", localDateTime);
      rewardState.setLastMinute(localDateTime.getMinute());
      rewardState.setCountDay(0L);
      rewardState.setCountHour(0L);
      rewardState.setCountMinute(0L);
      processStateMinuterQuantity(rewardState, rewardSchedule.getQuantity());
      saveUpdateStateLog(rewardState, localDateTime);
    }
    rewardStateStorage.save(rewardState);
  }

  public void processStateDayQuantity(RewardState rewardState, long quantity, long minuteToNextDay) {
    int processQuantity = 0;
    long notProcessQuantity = quantity - rewardState.getCountDay();

    if (notProcessQuantity >= minuteToNextDay) {
      //Nếu số quà có số lượng lớn hơn số phút còn lại thì cộng thêm bằng số quà còn lại / số phút còn lại
      processQuantity = (int) (notProcessQuantity / minuteToNextDay);
    } else {
      int minuteInDay = 60 * 24;
      //Nêu số quà có số lượng ít hơn so phut con lai
      int avgMinutePerReward = (int) ((minuteInDay) / notProcessQuantity);
      if (minuteToNextDay % avgMinutePerReward == 0) {
        processQuantity = 1;
      }
    }
    rewardState.addCountDay(processQuantity);
    rewardState.addCountHour(processQuantity);
    rewardState.addCountMinute(processQuantity);

  }


  public void processStateHourQuantity(RewardState rewardState, long quantity, long minuteToNextHour) {
    int processQuantity = 0;
    long notProcessQuantity = quantity - rewardState.getCountHour();
    if(notProcessQuantity == 0) {
      log.warn("=====>processStateHourQuantity: notProcessQuantity == 0");
      return;
    }
    if (notProcessQuantity >= minuteToNextHour) {
      //Nếu số quà có số lượng lớn hơn số phút còn lại thì cộng thêm bằng số quà còn lại / số phút còn lại
      processQuantity = (int) (notProcessQuantity / minuteToNextHour);
    } else {
      int minuteInHour = 60;
      //Nêu số quà có số lượng ít hơn so phut con lai
      int avgMinutePerReward = (int) ((minuteInHour) / notProcessQuantity);
      //Nếu chỉ còn 1 quà thì cần xu ly de khong tra ngay khi goi
      if(notProcessQuantity == 1){
        avgMinutePerReward = avgMinutePerReward * 2;
      }
      if (minuteToNextHour % avgMinutePerReward == 0) {
        processQuantity = 1;
      }
    }
    rewardState.addCountMinute(processQuantity);
    rewardState.addCountDay(processQuantity);
    rewardState.addCountHour(processQuantity);
  }

  public void processStateMinuterQuantity(RewardState rewardState, long quantity) {
    rewardState.addCountMinute(quantity);
    rewardState.addCountDay(quantity);
    rewardState.addCountHour(quantity);
  }

  public void saveUpdateStateLog(RewardState rewardState, LocalDateTime localDateTime) {
    RewardStateLog rewardStateLog = new RewardStateLog();
    rewardStateLog.setRewardStateId(rewardState.getId());
    rewardStateLog.setQuantityDay(rewardState.getCountDay());
    rewardStateLog.setQuantityHour(rewardState.getCountHour());
    rewardStateLog.setQuantityMinute(rewardState.getCountMinute());
    rewardStateLog.setCreatedTime(localDateTime);
    rewardStateLogStorage.save(rewardStateLog);
  }

  public Boolean initData() {
    for (long i = 1; i < 10; i++) {
      RewardItem rewardItem = new RewardItem();
      rewardItem.setId(i);
      rewardItem.setRewardName("item" + i);
      rewardItem.setRewardType(RewardType.VOUCHER);
      rewardItem.setDescription("Test data");
      rewardItem.setQuantity(0L);
      rewardItem.setStatus(Status.ACTIVE);
      rewardItem.setExternalId("123");
      rewardItem.setIsLimited(true);
      rewardItemStorage.save(rewardItem);
    }
    RewardSegment rewardSegment = new RewardSegment();
    rewardSegment.setId(1L);
    rewardSegment.setStatus(Status.ACTIVE);
    rewardSegment.setName("Test segment");
    rewardSegment.setCode("test");
    rewardSegment.setImageUrl("imgUrl");
    rewardSegmentStorage.save(rewardSegment);

    for (long i = 1; i < 10; i++) {
      RewardSegmentDetail rewardSegmentDetail = new RewardSegmentDetail();
      rewardSegmentDetail.setRewardItemId(i);
      rewardSegmentDetail.setRewardSegmentId(1L);
      rewardSegmentDetail.setPriority(10L);
      rewardSegmentDetail.setSegmentRate(10L);
      rewardSegmentDetail.setPosition(i - 1);
      rewardSegmentDetailStorage.save(rewardSegmentDetail);
    }

    for (long i = 1; i < 10; i++) {
      RewardSchedule rewardSchedule = new RewardSchedule();
      rewardSchedule.setId(i);
      rewardSchedule.setRewardSegmentDetailId(i);
      rewardSchedule.setQuantity(10L);
      rewardSchedule.setStartAt(0L);
      rewardSchedule.setEndAt(Long.MAX_VALUE);
      if (i % 3 == 0) {
        rewardSchedule.setPeriodType(PeriodType.DAY);
      } else if (i % 3 == 1) {
        rewardSchedule.setPeriodType(PeriodType.HOUR);
      } else {
        rewardSchedule.setPeriodType(PeriodType.MINUTE);
      }
      rewardSchedule.setPeriodType(PeriodType.DAY);
      rewardScheduleStorage.save(rewardSchedule);
    }
    return true;
  }
}
