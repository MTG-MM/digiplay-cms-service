package com.managersystem.admin.server.service;

import com.managersystem.admin.server.entities.*;
import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.entities.type.PollItemStatus;
import com.managersystem.admin.server.entities.type.StoreType;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
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
    if(voucherDetail == null){
      log.warn("===========> setVoucherDetailForUser: voucher detail not found {}", detailId);
      return null;
    }
    RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(voucherDetail.getStoreId());
    if(rewardItemStore == null){
      log.warn("===========> setVoucherDetailForUser: voucher store not found {}", voucherDetail.getStoreId());
      return null;
    }
    if(!rewardItemStore.getStatus().equals(Status.ACTIVE)){
      return null;
    }
    if(now < voucherDetail.getStartAt()){
      return null;
    }
    if(now > voucherDetail.getExpireAt()){
      voucherDetail.setStatus(PollItemStatus.EXPIRE);
    }else{
      voucherDetail.setGivenAt(now);
      voucherDetail.setUserId(user.getId());
      voucherDetail.setStatus(PollItemStatus.RECEIVE);
    }
    voucherDetailStorage.save(voucherDetail);
    return voucherDetail;
  }

  public List<VoucherDetail> getVoucherDetail(int voucherStoreId, int limit, RewardSchedule rewardSchedule, boolean newPeriod) {

    List<VoucherDetail> voucherDetails = null;
    try {
      if(newPeriod && !rewardSchedule.getIsAccumulative()){
        voucherDetailStorage.updateItemStatus(rewardSchedule.getRewardSegmentDetailId());
      }
      voucherDetails = voucherDetailStorage.getListVoucherDetailByStatus(voucherStoreId, PollItemStatus.NEW, limit);

      voucherDetails.forEach(v -> {
        v.setStatus(PollItemStatus.IN_POOL);
        v.setSegmentDetailId(rewardSchedule.getRewardSegmentDetailId());
      });
      voucherDetailStorage.saveAll(voucherDetails);
    } catch (Exception ex){
      log.error("======>getVoucherDetail {} {}" , voucherStoreId, ex);
    }
    return voucherDetails;
  }
  public void initRandomVoucherDetail(){
    RewardItemStore rewardItemStore = new RewardItemStore();
    rewardItemStore.setId(1L);
    rewardItemStore.setName("Voucher test");
    rewardItemStore.setStatus(Status.ACTIVE);
    rewardItemStore.setType(StoreType.VOUCHER);
    List<VoucherDetail> voucherDetails = new ArrayList<>();
    for(int i = 0; i < 1000; i++){
      VoucherDetail voucherDetail = new VoucherDetail();
      voucherDetail.setName(rewardItemStore.getName());
      voucherDetail.setCode(UUID.randomUUID().toString());
      voucherDetail.setStartAt(DateUtils.getNowMillisAtUtc());
      voucherDetail.setExpireAt(DateUtils.getNowMillisAtUtc() + 1000L * 60 * 60 * 24 * 100 );
      voucherDetail.setStoreId(rewardItemStore.getId());
      voucherDetail.setStatus(PollItemStatus.NEW);
      voucherDetails.add(voucherDetail);
    }
    voucherDetailStorage.saveAll(voucherDetails);
    rewardItemStoreStorage.save(rewardItemStore);
  }
}
