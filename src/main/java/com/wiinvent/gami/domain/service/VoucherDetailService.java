package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.entities.reward.RewardItemStore;
import com.wiinvent.gami.domain.entities.reward.RewardSchedule;
import com.wiinvent.gami.domain.entities.reward.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.pojo.VoucherExcelData;
import com.wiinvent.gami.domain.response.VoucherDetailResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.service.reward.RewardItemStoreService;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.entities.*;
import com.wiinvent.gami.domain.utils.ExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        voucherDetailStorage.updateItemStatus(rewardSegmentDetail.getRewardSegmentId(), rewardSegmentDetail.getRewardItemId(), rewardSchedule.getQuantity());
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

  public PageResponse<VoucherDetailResponse> getAllVoucherDetails(Long storeId, String name, String code, Pageable pageable) {
    Page<VoucherDetail> voucherDetails = voucherDetailStorage.findAllVoucherDetails(storeId, name, code, pageable);
    return PageResponse.createFrom(modelMapper.toPageVoucherDetailResponse(voucherDetails));
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void importVoucher(MultipartFile file, Long storeId) throws Exception {
    RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(storeId);
    if (rewardItemStore == null) {
      throw new ResourceNotFoundException("Cannot found Voucher with id: " + storeId);
    }

    List<VoucherExcelData> voucherExcelData = ExcelUtils.readExcel(file, VoucherExcelData.getHeader(), VoucherExcelData.class);
    List<VoucherDetail> voucherDetails = new ArrayList<>();
    for (VoucherExcelData excelData : voucherExcelData) {
      VoucherDetail voucherDetail = toVoucherDetail(excelData, storeId);
      voucherDetails.add(voucherDetail);
    }
    voucherDetailStorage.saveAll(voucherDetails);
  }

  public VoucherDetail toVoucherDetail(VoucherExcelData voucherExcelData, Long storeId) {
    VoucherDetail voucherDetail = new VoucherDetail();
    voucherDetail.setCode(voucherExcelData.getCode());
    voucherDetail.setName(voucherExcelData.getName());
    voucherDetail.setStoreId(storeId);
    voucherDetail.setStartAt(DateUtils.convertStringToLongUTC(voucherExcelData.getStartAt()));
    voucherDetail.setExpireAt(DateUtils.convertStringToLongUTC(voucherExcelData.getExpireAt()));
    return voucherDetail;
  }
}
