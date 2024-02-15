package com.managersystem.admin.server.service;

import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.entities.VoucherDetail;
import com.managersystem.admin.server.entities.VoucherStore;
import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.entities.type.VoucherStatus;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.springframework.scheduling.annotation.Async;
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
    VoucherStore voucherStore = voucherStoreStorage.findById(voucherDetail.getStoreId());
    if(voucherStore == null){
      log.warn("===========> setVoucherDetailForUser: voucher store not found {}", voucherDetail.getStoreId());
      return null;
    }
    if(!voucherStore.getStatus().equals(Status.ACTIVE)){
      return null;
    }
    if(now < voucherDetail.getStartAt()){
      return null;
    }
    if(now > voucherDetail.getExpireAt()){
      voucherDetail.setStatus(VoucherStatus.EXPIRE);
    }else{
      voucherDetail.setGivenAt(now);
      voucherDetail.setUserId(user.getId());
      voucherDetail.setStatus(VoucherStatus.RECEIVE);
    }
    voucherDetailStorage.save(voucherDetail);
    return voucherDetail;
  }

  public List<VoucherDetail> getVoucherDetail(int voucherStoreId, int limit) {

    RLock rLock = lockManager.startLockVoucher(voucherStoreId);
    List<VoucherDetail> voucherDetails = null;
    try {
      voucherDetails = voucherDetailStorage.getListVoucherDetailByStatus(voucherStoreId, VoucherStatus.NEW, limit);

      voucherDetails.forEach(v -> v.setStatus(VoucherStatus.IN_POOL));
      voucherDetailStorage.saveAll(voucherDetails);
    } catch (Exception ex){
      log.error("======>getVoucherDetail {}" , voucherStoreId);
    } finally {
      lockManager.unLock(rLock);
    }
    return voucherDetails;
  }
  public void initRandomVoucherDetail(){
    VoucherStore voucherStore = new VoucherStore();
    voucherStore.setId(1L);
    voucherStore.setName("Voucher test");
    voucherStore.setStatus(Status.ACTIVE);
    List<VoucherDetail> voucherDetails = new ArrayList<>();
    for(int i = 0; i < 1000; i++){
      VoucherDetail voucherDetail = new VoucherDetail();
      voucherDetail.setName(voucherStore.getName());
      voucherDetail.setCode(UUID.randomUUID().toString());
      voucherDetail.setStartAt(DateUtils.getNowMillisAtUtc());
      voucherDetail.setExpireAt(DateUtils.getNowMillisAtUtc() + 1000L * 60 * 60 * 24 * 100 );
      voucherDetail.setStoreId(voucherStore.getId());
      voucherDetail.setStatus(VoucherStatus.NEW);
      voucherDetails.add(voucherDetail);
    }
    voucherDetailStorage.saveAll(voucherDetails);
    voucherStoreStorage.save(voucherStore);
  }
}
