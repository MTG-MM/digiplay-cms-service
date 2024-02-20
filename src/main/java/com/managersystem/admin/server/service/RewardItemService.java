package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardItemDto;
import com.managersystem.admin.handleRequest.controller.response.RewardItemResponse;
import com.managersystem.admin.handleRequest.controller.response.RewardResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.*;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RewardItemService extends BaseService {

  @Autowired UserService userService;
  @Autowired VoucherDetailService voucherDetailService;
  @Autowired ProductDetailService productDetailService;

//  public PageResponse<RewardItemResponse> getAllRewardItems(Pageable pageable) {
//    Page<RewardItem> rewardItems = rewardItemStorage.findAll(pageable);
//    Page<RewardItemResponse> responses = modelMapper.toPageRewardItemResponse(rewardItems);
//    return PageResponse.createFrom(responses);
//  }

  public PageResponse<RewardItemResponse> getAll
      (Integer id, String name, com.managersystem.admin.server.entities.type.RewardType type, Pageable pageable) {
    Page<RewardItem> rwItems = rewardItemStorage.findAll(rwItemCondition(id, name, type), pageable);
    Page<RewardItemResponse> responses = modelMapper.toPageRewardItemResponse(rwItems);
    return PageResponse.createFrom(responses);
  }

  public Specification<RewardItem> rwItemCondition(
      Integer id, String name, com.managersystem.admin.server.entities.type.RewardType type) {
    return (rwItem, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      if (id != null) {
        conditionsList.add(criteriaBuilder.equal(rwItem.get("id"), id));
      }
      if (name != null) {
        conditionsList.add(criteriaBuilder.like(rwItem.get("rewardName"), "%" + name + "%"));
      }
      if (type != null) {
        conditionsList.add(criteriaBuilder.equal(rwItem.get("rewardType"), type));
      }
      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }

  public Long createRewardItems(RewardItemDto rewardItemDto) {
    RewardItem rewardItem = modelMapper.toRewardItem(rewardItemDto);
    RewardType rewardType = rewardTypeStorage.findById(rewardItemDto.getRewardTypeId());
    if (rewardType == null){
      throw new ResourceNotFoundException("reward type not found");
    }
    rewardItem.setRewardTypeId(rewardType.getId());
    rewardItemStorage.save(rewardItem);
    return rewardItem.getId();
  }

  public Boolean updateRewardItems(Long id, RewardItemDto rewardItemDto) {
    RewardItem rewardItem = rewardItemStorage.findById(id);
    if (rewardItem == null){
      throw new ResourceNotFoundException("item not found");
    }
    RewardType rewardType = rewardTypeStorage.findById(rewardItemDto.getRewardTypeId());
    if (rewardType == null){
      throw new ResourceNotFoundException("reward type not found");
    }
    rewardItem.setRewardTypeId(rewardType.getId());
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

  @Transactional(propagation = Propagation.MANDATORY)
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
      case PRODUCT -> {
        UUID detailId = getRewardItemInCache(segmentDetail);
        if(detailId == null){
          return null;
        }
        ProductDetail productDetail = productDetailService.setProductDetailForUser(user, detailId);
        if(productDetail != null){
          return new RewardResponse(rewardItem, segmentDetail, productDetail);
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
