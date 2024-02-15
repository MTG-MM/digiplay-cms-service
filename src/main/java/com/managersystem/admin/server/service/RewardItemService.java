package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardItemDto;
import com.managersystem.admin.handleRequest.controller.response.RewardItemResponse;
import com.managersystem.admin.handleRequest.controller.response.RewardResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.entities.VoucherDetail;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RewardItemService extends BaseService {

  @Autowired UserService userService;

  @Autowired VoucherDetailService voucherDetailService;

  public PageResponse<RewardItemResponse> getAllRewardItems(Pageable pageable) {
    Page<RewardItem> rewardItems = rewardItemStorage.findAll(pageable);
    Page<RewardItemResponse> responses = modelMapper.toPageRewardItemResponse(rewardItems);
    return PageResponse.createFrom(responses);
  }

  public boolean createRewardItems(RewardItemDto rewardItemDto) {
    RewardItem rewardItem = modelMapper.toRewardItem(rewardItemDto);
    rewardItemStorage.save(rewardItem);
    return true;
  }

  public Boolean updateRewardItems(Long id, RewardItemDto rewardItemDto) {
    RewardItem rewardItem = rewardItemStorage.findById(id);
    if (rewardItem == null){
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapRewardItemDtoToRewardItem(rewardItemDto, rewardItem);
    rewardItemStorage.save(rewardItem);
    return true;
  }

  public RewardItemResponse getRewardItemDetail(Long id) {
    RewardItem rewardItem = rewardItemStorage.findById(id);
    if (rewardItem == null){
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardItemResponse(rewardItem);
  }

  public RewardResponse processReturnRewardItem(User user, RewardSegmentDetail segmentDetail){
    RewardItem rewardItem = rewardItemStorage.findById(segmentDetail.getRewardItemId());
    if(rewardItem == null){
      return null;
    }

    switch (rewardItem.getRewardType()){
      case POINT -> {
        userService.addPointForUser(user, rewardItem.getValue());
      }
      case VOUCHER -> {
        UUID detailId = getRewardItemInCache(segmentDetail);
        if(detailId == null){
          return null;
        }
        VoucherDetail voucherDetail = voucherDetailService.setVoucherDetailForUser(user, detailId);
        if(voucherDetail != null){
          return new RewardResponse(rewardItem, segmentDetail, voucherDetail);
        }
      }
      case PHYSICAL -> {
        UUID detailId = getRewardItemInCache(segmentDetail);
        if(detailId == null){
          return null;
        }
      }
      default -> {
        return null;
      }
    }
    return null;
  }

  /**
   * Lay id qua ma schedule da them vao
   * @return id qua con lai
   */
  public UUID getRewardItemInCache(RewardSegmentDetail segmentDetail){
    return remoteCache.rDequePoolFirst(cacheKey.getRewardPoolItemIds(segmentDetail.getRewardSegmentId(), segmentDetail.getRewardItemId()));
  }
}
