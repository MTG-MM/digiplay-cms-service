package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.entities.reward.RewardItemStore;
import com.wiinvent.gami.domain.entities.reward.RewardSchedule;
import com.wiinvent.gami.domain.entities.reward.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.response.VoucherDetailResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.entities.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class VoucherDetailService extends BaseService {
  public VoucherDetail setVoucherDetailForUser(User user, UUID detailId) {
    Long now = DateUtils.getNowMillisAtUtc();
    VoucherDetail voucherDetail = voucherDetailStorage.findById(detailId);
    if (voucherDetail == null) {
      log.warn("===========> setVoucherDetailForUser: voucher detail not found {}", detailId);
      return null;
    }
    RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(voucherDetail.getStoreId());
    if (rewardItemStore == null) {
      log.warn("===========> setVoucherDetailForUser: voucher store not found {}", voucherDetail.getStoreId());
      return null;
    }
    if (!rewardItemStore.getStatus().equals(Status.ACTIVE)) {
      return null;
    }
    if (now < voucherDetail.getStartAt()) {
      return null;
    }
    if (now > voucherDetail.getExpireAt()) {
      voucherDetail.setStatus(RewardItemStatus.EXPIRE);
    } else {
      voucherDetail.setGivenAt(now);
      voucherDetail.setUserId(user.getId());
      voucherDetail.setStatus(RewardItemStatus.RECEIVE);
    }
    voucherDetailStorage.save(voucherDetail);
    return voucherDetail;
  }

  public List<VoucherDetail> getVoucherDetail(int voucherStoreId, int limit, RewardSchedule rewardSchedule, RewardSegmentDetail rewardSegmentDetail, boolean newPeriod) {

    List<VoucherDetail> voucherDetails = null;
    try {
      if (newPeriod && Boolean.FALSE.equals(rewardSchedule.getIsAccumulative())) {
        voucherDetailStorage.updateItemStatus(rewardSegmentDetail.getRewardSegmentId(), rewardSegmentDetail.getRewardItemId(),rewardSchedule.getQuantity() );
      }
      voucherDetails = voucherDetailStorage.getListVoucherDetailByStatus(voucherStoreId, RewardItemStatus.NEW, limit);

      voucherDetails.forEach(v -> {
        v.setStatus(RewardItemStatus.IN_POOL);
        v.setRewardItemId(rewardSegmentDetail.getRewardItemId());
        v.setRewardSegmentId(rewardSegmentDetail.getRewardSegmentId());
        v.setGivenToPool(DateUtils.getNowMillisAtUtc());
      });
      voucherDetailStorage.saveAll(voucherDetails);
    } catch (Exception ex) {
      log.error("======>getVoucherDetail {} {}", voucherStoreId, ex);
    }
    return voucherDetails;
  }

  public void initRandomVoucherDetail() {
    RewardItemStore rewardItemStore = new RewardItemStore();
    rewardItemStore.setId(1L);
    rewardItemStore.setName("Voucher test");
    rewardItemStore.setStatus(Status.ACTIVE);
    rewardItemStore.setType(StoreType.VOUCHER);
    List<VoucherDetail> voucherDetails = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      VoucherDetail voucherDetail = new VoucherDetail();
      voucherDetail.setName(rewardItemStore.getName());
      voucherDetail.setCode(UUID.randomUUID().toString());
      voucherDetail.setStartAt(DateUtils.getNowMillisAtUtc());
      voucherDetail.setExpireAt(DateUtils.getNowMillisAtUtc() + 1000L * 60 * 60 * 24 * 100);
      voucherDetail.setStoreId(rewardItemStore.getId());
      voucherDetail.setStatus(RewardItemStatus.NEW);
      voucherDetails.add(voucherDetail);
    }
    voucherDetailStorage.saveAll(voucherDetails);
    rewardItemStoreStorage.save(rewardItemStore);
  }

  public PageResponse<VoucherDetailResponse> getAllVoucherDetails(Long storeId, Pageable pageable) {
    Page<VoucherDetail> voucherDetails = voucherDetailStorage.findByStoreId(storeId, pageable);
    return PageResponse.createFrom(modelMapper.toPageVoucherDetailResponse(voucherDetails));
  }
}
