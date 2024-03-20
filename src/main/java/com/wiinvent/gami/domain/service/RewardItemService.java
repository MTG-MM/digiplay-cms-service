package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.RewardItemDto;
import com.wiinvent.gami.domain.response.RewardItemResponse;
import com.wiinvent.gami.domain.response.RewardResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.entities.*;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class RewardItemService extends BaseService {

  @Autowired
  UserService userService;
  @Autowired
  VoucherDetailService voucherDetailService;
  @Autowired
  ProductDetailService productDetailService;
  @Autowired
  RewardItemHistoryService rewardItemHistoryService;

//  public PageResponse<RewardItemResponse> getAllRewardItems(Pageable pageable) {
//    Page<RewardItem> rewardItems = rewardItemStorage.findAll(pageable);
//    Page<RewardItemResponse> responses = modelMapper.toPageRewardItemResponse(rewardItems);
//    return PageResponse.createFrom(responses);
//  }

  public PageResponse<RewardItemResponse> getAll
      (Integer id, String name, RewardItemType type, Pageable pageable) {
    Page<RewardItem> rwItems = rewardItemStorage.findAll(rwItemCondition(id, name, type), pageable);
    Page<RewardItemResponse> responses = modelMapper.toPageRewardItemResponse(rwItems);
    return PageResponse.createFrom(responses);
  }

  public Specification<RewardItem> rwItemCondition(
      Integer id, String name, RewardItemType type) {
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
    com.wiinvent.gami.domain.entities.RewardType rewardType = rewardTypeStorage.findById(rewardItemDto.getRewardTypeId());
    if (rewardType == null) {
      throw new ResourceNotFoundException("reward type not found");
    }
    rewardItem.setRewardTypeId(rewardType.getId());
    rewardItemStorage.save(rewardItem);
    return rewardItem.getId();
  }

  public Boolean updateRewardItems(Long id, RewardItemDto rewardItemDto) {
    RewardItem rewardItem = rewardItemStorage.findById(id);
    if (rewardItem == null) {
      throw new ResourceNotFoundException("item not found");
    }
    com.wiinvent.gami.domain.entities.RewardType rewardType = rewardTypeStorage.findById(rewardItemDto.getRewardTypeId());
    if (rewardType == null) {
      throw new ResourceNotFoundException("reward type not found");
    }
    rewardItem.setRewardTypeId(rewardType.getId());
    modelMapper.mapRewardItemDtoToRewardItem(rewardItemDto, rewardItem);
    rewardItemStorage.save(rewardItem);
    return true;
  }

  public RewardItemResponse getRewardItemDetail(Long id) {
    RewardItem rewardItem = rewardItemStorage.findById(id);
    if (rewardItem == null) {
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardItemResponse(rewardItem);
  }

}
