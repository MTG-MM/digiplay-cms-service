package com.wiinvent.gami.domain.service.reward;

import com.wiinvent.gami.domain.dto.RewardScheduleDto;
import com.wiinvent.gami.domain.dto.RewardScheduleUpdateDto;
import com.wiinvent.gami.domain.entities.reward.*;
import com.wiinvent.gami.domain.entities.type.*;
import com.wiinvent.gami.domain.response.RewardScheduleResponse;
import com.wiinvent.gami.domain.entities.*;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.service.ProductDetailService;
import com.wiinvent.gami.domain.service.VoucherDetailService;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RewardScheduleService extends BaseService {

  @Autowired
  VoucherDetailService voucherDetailService;
  @Autowired
  ProductDetailService productDetailService;
  @Autowired
  @Lazy
  RewardScheduleService self;

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
    if (rewardSchedule == null) {
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapRewardScheduleUpdateDtoToRewardSchedule(rewardScheduleDto, rewardSchedule);
    rewardScheduleStorage.save(rewardSchedule);
    return true;
  }

  public RewardScheduleResponse getRewardScheduleDetail(Long id) {
    RewardSchedule rewardSchedule = rewardScheduleStorage.findById(id);
    if (rewardSchedule == null) {
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardScheduleResponse(rewardSchedule);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void addRewardSegmentQuantity() {
    List<RewardSegment> rewardSegmentEntities = rewardSegmentStorage.findByStatus(Status.ACTIVE);
    List<RewardItem> rewardItems = rewardItemStorage.findAll();
    Map<Long, RewardItem> rewardItemMap = rewardItems.stream().collect(Collectors.toMap(RewardItem::getId, Function.identity()));
    rewardSegmentEntities.forEach(rs -> self.processAddRewardSegmentDetail(rs.getId(), rewardItemMap));
  }

  @Transactional(propagation = Propagation.MANDATORY)
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
      self.processAddQuantity(rewardSchedule, rewardItem, rewardSegmentDetail);
    });
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void processAddQuantity(RewardSchedule rewardSchedule, RewardItem rewardItem, RewardSegmentDetail segmentDetail) {
    boolean newPeriod = false;
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
        newPeriod = true;
        rewardState.setLastDay(localDateTime.getDayOfMonth());
        rewardState.setCountDay(0L);
        rewardState.setCountHour(0L);
        rewardState.setCountMinute(0L);
      }
      int minutesToNextDay = 24 * 60 - currentMinute;
      long quantity = processStateDayQuantity(rewardState, rewardSchedule.getQuantity(), minutesToNextDay, localDateTime.getDayOfMonth());
      if (quantity > 0) {
        rewardState.setLastMinute(currentMinute);
        self.processUpdatePoolItem((int) quantity, rewardItem, rewardSchedule, newPeriod, segmentDetail);
      }
      saveUpdateStateLog(rewardState, localDateTime, quantity);
    } else if (rewardSchedule.getPeriodType() == PeriodType.HOUR) {
      if (localDateTime.getHour() != rewardState.getLastHour()) {
        log.debug("====>update new hour {}", localDateTime);
        newPeriod = true;
        rewardState.setLastHour(localDateTime.getHour());
        rewardState.setCountDay(0L);
        rewardState.setCountHour(0L);
        rewardState.setCountMinute(0L);
      }
      int minutesToNextHour = 60 - localDateTime.getMinute();
      long quantity = processStateHourQuantity(rewardState, rewardSchedule.getQuantity(), minutesToNextHour, localDateTime.getHour());
      if (quantity > 0) {
        rewardState.setLastMinute(currentMinute);
        self.processUpdatePoolItem((int) quantity, rewardItem, rewardSchedule, newPeriod, segmentDetail);
      }
      saveUpdateStateLog(rewardState, localDateTime, quantity);
    } else if (rewardSchedule.getPeriodType() == PeriodType.MINUTE) {
      log.debug("====>update new minute {}", localDateTime);
      rewardState.setLastMinute(localDateTime.getMinute());
      rewardState.setCountDay(0L);
      rewardState.setCountHour(0L);
      rewardState.setCountMinute(0L);
      long quantity = processStateMinuterQuantity(rewardState, rewardSchedule.getQuantity(), currentMinute);
      if (quantity > 0) {
        rewardState.setLastMinute(currentMinute);
        self.processUpdatePoolItem((int) quantity, rewardItem, rewardSchedule, true, segmentDetail);
      }
      saveUpdateStateLog(rewardState, localDateTime, quantity);
    }
    rewardStateStorage.save(rewardState);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void processUpdatePoolItem(int amount, RewardItem rewardItem, RewardSchedule rewardSchedule, boolean newPeriod, RewardSegmentDetail rewardSegment) {
    long numberItemRemoved = 0;
    if (newPeriod && Boolean.TRUE.equals(!rewardSchedule.getIsAccumulative())) {
      for (int i = 0; i < rewardSchedule.getQuantity(); i++) {
        UUID itemIdRemove = remoteCache.rDequePoolFirst(cacheKey.getRewardPoolItemIds(rewardSegment.getRewardSegmentId(), rewardItem.getId()));
        if (itemIdRemove != null) {
          numberItemRemoved++;
        }
      }
      log.debug("====>processUpdatePoolItem remove item: {}", numberItemRemoved);
    }
    switch (rewardItem.getRewardType()) {
      case POINT -> {
      }
      case VOUCHER -> {
        List<VoucherDetail> voucherDetails = voucherDetailService.getVoucherDetail(Integer.parseInt(rewardItem.getExternalId()), amount, rewardSchedule, rewardSegment, newPeriod);
        if (!voucherDetails.isEmpty()) {
          voucherDetails.forEach(v ->
              remoteCache.rDequePutId(cacheKey.getRewardPoolItemIds(rewardSegment.getRewardSegmentId(), rewardItem.getId()), v.getId())
          );
        }

      }
      case PRODUCT -> {
        List<ProductDetail> productDetails = productDetailService.getProductDetail(Integer.parseInt(rewardItem.getExternalId()), amount, rewardSchedule, rewardSegment, newPeriod);
        if (!productDetails.isEmpty()) {
          productDetails.forEach(v ->
              remoteCache.rDequePutId(cacheKey.getRewardPoolItemIds(rewardSegment.getRewardSegmentId(), rewardItem.getId()), v.getId())
          );
        }
      }
    }
  }

  public long processStateDayQuantity(RewardState rewardState, long quantity, long minuteToNextDay, int time) {
    long processQuantity = 0;
    long notProcessQuantity = quantity - rewardState.getCountDay();
    if (notProcessQuantity == 0) {
      log.warn("=====>processStateHourQuantity: notProcessQuantity == 0");
      return 0;
    }
    if (notProcessQuantity >= minuteToNextDay) {
      //Nếu số quà có số lượng lớn hơn số phút còn lại thì cộng thêm bằng số quà còn lại / số phút còn lại
      processQuantity = Helper.numberAround(notProcessQuantity, minuteToNextDay);
    } else {
      int minuteInDay = 60 * 24;
      //Nêu số quà có số lượng ít hơn so phut con lai
      int avgMinutePerReward = (int) ((minuteInDay) / notProcessQuantity);
      //Nếu chỉ còn 1 quà thì cần xu ly de khong tra ngay khi goi
      if (notProcessQuantity == 1) {
        avgMinutePerReward = avgMinutePerReward * 2;
      }
      if (minuteToNextDay % avgMinutePerReward == 0) {
        processQuantity = 1;
      }
    }
    if (processQuantity > 0) {
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
    if (notProcessQuantity == 0) {
      log.warn("=====>processStateHourQuantity: notProcessQuantity == 0");
      return 0;
    }
    if (notProcessQuantity >= minuteToNextHour) {
      //Nếu số quà có số lượng lớn hơn số phút còn lại thì cộng thêm bằng số quà còn lại / số phút còn lại
      processQuantity = Helper.numberAround(notProcessQuantity, minuteToNextHour);
    } else {
      int minuteInHour = 60;
      //Nêu số quà có số lượng ít hơn so phut con lai
      int avgMinutePerReward = (int) ((minuteInHour) / notProcessQuantity);
      //Nếu chỉ còn 1 quà thì cần xu ly de khong tra ngay khi goi
      if (notProcessQuantity == 1) {
        avgMinutePerReward = avgMinutePerReward * 2;
      }
      if (minuteToNextHour % avgMinutePerReward == 0) {
        processQuantity = 1;
      }
    }
    if (processQuantity > 0) {
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
    // Create and save RewardItemStores
    for (long i = 1; i <= 5; i++) {
      RewardItemStore rewardItemStore = new RewardItemStore();
      rewardItemStore.setId(i);
      rewardItemStore.setName("Store voucher " + i);
      rewardItemStore.setStatus(Status.ACTIVE);
      rewardItemStore.setType(StoreType.VOUCHER); // You can set the type according to your requirements
      rewardItemStore.setCreatedAt(DateUtils.getNowMillisAtUtc());
      rewardItemStore.setUpdatedAt(DateUtils.getNowMillisAtUtc());
      rewardItemStoreStorage.save(rewardItemStore);
    }

    for (long i = 6; i <= 10; i++) {
      RewardItemStore rewardItemStore = new RewardItemStore();
      rewardItemStore.setId(i);
      rewardItemStore.setName("Store product" + i);
      rewardItemStore.setStatus(Status.ACTIVE);
      rewardItemStore.setType(StoreType.PRODUCT); // You can set the type according to your requirements
      rewardItemStore.setCreatedAt(DateUtils.getNowMillisAtUtc());
      rewardItemStore.setUpdatedAt(DateUtils.getNowMillisAtUtc());
      rewardItemStoreStorage.save(rewardItemStore);
    }

    for (long i = 1; i <= 10; i++) {
      RewardItem rewardItem = new RewardItem();
      rewardItem.setId(i);
      rewardItem.setExternalId("externalId" + i); // externalId có thể được tạo ngẫu nhiên hoặc theo logic của bạn
      rewardItem.setStatus(Status.ACTIVE);
      rewardItem.setRewardType(i > 5 ? RewardItemType.PRODUCT : RewardItemType.VOUCHER); // Bạn có thể đặt loại tùy theo yêu cầu của mình
      rewardItem.setCreatedAt(DateUtils.getNowMillisAtUtc());
      rewardItem.setUpdatedAt(DateUtils.getNowMillisAtUtc());
      rewardItemStorage.save(rewardItem);
    }


    // Create and save RewardItems (Vouchers)
    for (long i = 1; i <= 100000; i++) {
      VoucherDetail voucherDetail = new VoucherDetail();
      voucherDetail.setStoreId(Helper.randomBetween(1,5)); // Assign store IDs cyclically
      voucherDetail.setName("Voucher " + i);
      voucherDetail.setCode("V" + i);
      voucherDetail.setUserId(null);
      voucherDetail.setGivenAt(null);
      voucherDetail.setStatus(RewardItemStatus.NEW);
      voucherDetail.setGivenToPool(0L);
      voucherDetail.setRewardSegmentId(1L);
      voucherDetail.setRewardItemId(i);
      voucherDetail.setStartAt(0L);
      voucherDetail.setExpireAt(DateUtils.getNowMillisAtUtc() + DateUtils.getNowMillisAtUtc());
      voucherDetailStorage.save(voucherDetail);
    }

    // Create and save RewardItems (Products)
    for (long i = 1; i <= 100000; i++) {
      ProductDetail productDetail = new ProductDetail();
      productDetail.setStoreId(Helper.randomBetween(6,10)); // Assign store IDs cyclically
      productDetail.setName("Product " + i);
      productDetail.setCode("P" + i);
      productDetail.setUserId(null);
      productDetail.setGivenAt(null);
      productDetail.setStatus(RewardItemStatus.NEW);
      productDetail.setGivenToPool(0L);
      productDetail.setRewardSegmentId(1L);
      productDetail.setRewardItemId(i);
      productDetail.setStartAt(0L);
      productDetail.setExpireAt(DateUtils.getNowMillisAtUtc() + DateUtils.getNowMillisAtUtc());
      productDetailStorage.save(productDetail);
    }

    // Create and save RewardSegment
    RewardSegment rewardSegment = new RewardSegment();
    rewardSegment.setId(1L);
    rewardSegment.setStatus(Status.ACTIVE);
    rewardSegment.setName("Test segment");
    rewardSegment.setCode("test");
    rewardSegment.setImageUrl("imgUrl");
    rewardSegmentStorage.save(rewardSegment);

    // Create and save RewardSegmentDetails
    for (long i = 1; i <= 100000; i++) {
      RewardSegmentDetail rewardSegmentDetail = new RewardSegmentDetail();
      rewardSegmentDetail.setRewardItemId(i);
      rewardSegmentDetail.setRewardSegmentId(1L);
      rewardSegmentDetail.setPriority(10L);
      rewardSegmentDetail.setSegmentRate(10L);
      rewardSegmentDetail.setPosition(i - 1);
      rewardSegmentDetailStorage.save(rewardSegmentDetail);
    }

    // Create and save RewardSchedules
    for (long i = 1; i <= 10; i++) {
      RewardSchedule rewardSchedule = new RewardSchedule();
      rewardSchedule.setId(i);
      rewardSchedule.setRewardSegmentDetailId(i);
      rewardSchedule.setQuantity(10L);
      rewardSchedule.setStartAt(0L);
      rewardSchedule.setEndAt(Long.MAX_VALUE);
      rewardSchedule.setPeriodType(PeriodType.values()[(int) (i % PeriodType.values().length)]); // Cycle through PeriodType values
      rewardScheduleStorage.save(rewardSchedule);
    }

    return true;
  }


  public boolean pushToRDeque(String key) {
//    int[] arr = new int[] {};
    for (int i = 0; i < 100000; i++) {
//      arr[i] = i;
      System.out.println(i);
      remoteCache.rDequePutId(key, i);
    }
//    remoteCache.rDequePutId(key, arr);
    return true;
  }

  public List<String> getFromRDeque(String key) {
    List<String> dataStrings = remoteCache.rDequeGetAll(key);
    System.out.println("dataStrings" + dataStrings.size());
    return dataStrings;
  }

  public boolean rDequePeekFirst(String key) {
    for (int i = 0; i < 10000; i++) {
      peekItem(key);
    }
    return true;
  }

  public void peekItem(String key) {
    Integer data = remoteCache.rDequePoolFirst(key);
    System.out.println(data);
  }

}
