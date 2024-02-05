package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardScheduleDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardScheduleUpdateDto;
import com.managersystem.admin.handleRequest.controller.response.RewardScheduleResponse;
import com.managersystem.admin.server.entities.*;
import com.managersystem.admin.server.entities.type.PeriodType;
import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.DateUtils;
import com.managersystem.admin.server.utils.Helper;
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

  public List<RewardScheduleResponse> getAllRewardSchedules(Long rewardSegmentId) {
    return modelMapper.toListRewardScheduleResponse(rewardScheduleStorage.findByRewardSegmentDetailId(rewardSegmentId));
  }

  public boolean createRewardSchedules(RewardScheduleDto rewardScheduleDto) {
    RewardSchedule rewardSchedule = modelMapper.toRewardSchedule(rewardScheduleDto);
    rewardScheduleStorage.save(rewardSchedule);
    return true;
  }

  public Boolean updateRewardSchedules(Long id, RewardScheduleUpdateDto rewardScheduleDto) {
    RewardSchedule rewardSchedule = rewardScheduleStorage.findById(id);
    if (rewardSchedule == null){
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapRewardScheduleUpdateDtoToRewardSchedule(rewardScheduleDto, rewardSchedule);
    rewardScheduleStorage.save(rewardSchedule);
    return true;
  }

  public RewardScheduleResponse getRewardScheduleDetail(Long id) {
    RewardSchedule rewardSchedule = rewardScheduleStorage.findById(id);
    if (rewardSchedule == null){
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardScheduleResponse(rewardSchedule);
  }

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
    int currentMinute = localDateTime.getHour() * 60 + localDateTime.getMinute();
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
      int minutesToNextDay = 24 * 60 - currentMinute;
      long quantity = processStateDayQuantity(rewardState, rewardSchedule.getQuantity(), minutesToNextDay, localDateTime.getDayOfMonth());
      if(quantity > 0) {
        rewardState.setLastMinute(currentMinute);
      }
      saveUpdateStateLog(rewardState, localDateTime, quantity);
    } else if (rewardSchedule.getPeriodType() == PeriodType.HOUR) {
      if (localDateTime.getHour() != rewardState.getLastHour()) {
        log.debug("====>update new hour {}", localDateTime);
        rewardState.setLastHour(localDateTime.getHour());
        rewardState.setCountDay(0L);
        rewardState.setCountHour(0L);
        rewardState.setCountMinute(0L);
      }
      int minutesToNextHour = 60 - localDateTime.getMinute();
      long quantity = processStateHourQuantity(rewardState, rewardSchedule.getQuantity(), minutesToNextHour, localDateTime.getHour());
      if(quantity > 0) {
        rewardState.setLastMinute(currentMinute);
      }
      saveUpdateStateLog(rewardState, localDateTime, quantity);
    } else if (rewardSchedule.getPeriodType() == PeriodType.MINUTE) {
      log.debug("====>update new minute {}", localDateTime);
      rewardState.setLastMinute(localDateTime.getMinute());
      rewardState.setCountDay(0L);
      rewardState.setCountHour(0L);
      rewardState.setCountMinute(0L);
      long quantity = processStateMinuterQuantity(rewardState, rewardSchedule.getQuantity(), currentMinute);
      if(quantity > 0) {
        rewardState.setLastMinute(currentMinute);
      }
      saveUpdateStateLog(rewardState, localDateTime, quantity);
    }
    rewardStateStorage.save(rewardState);
  }

  public long processStateDayQuantity(RewardState rewardState, long quantity, long minuteToNextDay, int time) {
    long processQuantity = 0;
    long notProcessQuantity = quantity - rewardState.getCountDay();
    if(notProcessQuantity == 0) {
      log.warn("=====>processStateHourQuantity: notProcessQuantity == 0");
      return 0;
    }
    if (notProcessQuantity >= minuteToNextDay) {
      //Nếu số quà có số lượng lớn hơn số phút còn lại thì cộng thêm bằng số quà còn lại / số phút còn lại
      processQuantity = Helper.numberAround(notProcessQuantity ,minuteToNextDay);
    } else {
      int minuteInDay = 60 * 24;
      //Nêu số quà có số lượng ít hơn so phut con lai
      int avgMinutePerReward = (int) ((minuteInDay) / notProcessQuantity);
      //Nếu chỉ còn 1 quà thì cần xu ly de khong tra ngay khi goi
      if(notProcessQuantity == 1){
        avgMinutePerReward = avgMinutePerReward * 2;
      }
      if (minuteToNextDay % avgMinutePerReward == 0) {
        processQuantity = 1;
      }
    }
    if(processQuantity > 0) {
      rewardState.addCountDay(processQuantity);
      rewardState.addCountHour(processQuantity);
      rewardState.addCountMinute(processQuantity);
      rewardState.setLastDay(time);
    }
    return processQuantity;
  }


  public long processStateHourQuantity(RewardState rewardState, long quantity, long minuteToNextHour, int time) {
    long processQuantity = 0;
    long notProcessQuantity = quantity - rewardState.getCountHour();
    if(notProcessQuantity == 0) {
      log.warn("=====>processStateHourQuantity: notProcessQuantity == 0");
      return 0;
    }
    if (notProcessQuantity >= minuteToNextHour) {
      //Nếu số quà có số lượng lớn hơn số phút còn lại thì cộng thêm bằng số quà còn lại / số phút còn lại
      processQuantity = Helper.numberAround(notProcessQuantity , minuteToNextHour);
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
    if(processQuantity > 0) {
      rewardState.addCountDay(processQuantity);
      rewardState.addCountHour(processQuantity);
      rewardState.addCountMinute(processQuantity);
      rewardState.setLastHour(time);
    }
    return processQuantity;
  }

  public long processStateMinuterQuantity(RewardState rewardState, long quantity, int time) {
    rewardState.addCountMinute(quantity);
    rewardState.addCountDay(quantity);
    rewardState.addCountHour(quantity);
    rewardState.setLastMinute(time);
    return quantity;
  }

  public void saveUpdateStateLog(RewardState rewardState, LocalDateTime localDateTime, long updateQuantity) {
    RewardStateLog rewardStateLog = new RewardStateLog();
    rewardStateLog.setRewardStateId(rewardState.getId());
    rewardStateLog.setQuantityDay(rewardState.getCountDay());
    rewardStateLog.setQuantityHour(rewardState.getCountHour());
    rewardStateLog.setQuantityMinute(rewardState.getCountMinute());
    rewardStateLog.setUpdateQuantity(updateQuantity);
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
