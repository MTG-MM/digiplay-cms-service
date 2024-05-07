package com.wiinvent.gami.domain.service.reward;

import com.wiinvent.gami.domain.dto.ProcessQuantityDto;
import com.wiinvent.gami.domain.dto.RewardItemDto;
import com.wiinvent.gami.domain.dto.RewardItemUpdateDto;
import com.wiinvent.gami.domain.entities.ExchangeItemStore;
import com.wiinvent.gami.domain.entities.ProductDetail;
import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.reward.RewardItemStore;
import com.wiinvent.gami.domain.entities.reward.RewardType;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.RewardItemResponse;
import com.wiinvent.gami.domain.response.RewardItemStoreResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.service.ProductDetailService;
import com.wiinvent.gami.domain.service.user.UserService;
import com.wiinvent.gami.domain.service.VoucherDetailService;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
public class RewardItemService extends BaseService {
  @Autowired
  @Lazy
  RewardItemService self;

//  public PageResponse<RewardItemResponse> getAllRewardItems(Pageable pageable) {
//    Page<RewardItem> rewardItems = rewardItemStorage.findAll(pageable);
//    Page<RewardItemResponse> responses = modelMapper.toPageRewardItemResponse(rewardItems);
//    return PageResponse.createFrom(responses);
//  }

  public PageResponse<RewardItemResponse> getAll
      (Integer id, String name, RewardItemType type, Pageable pageable) {
    Page<RewardItem> rwItems = rewardItemStorage.findAll(rwItemCondition(id, name, type), pageable);
    Page<RewardItemResponse> responses = modelMapper.toPageRewardItemResponse(rwItems);
    for (RewardItemResponse rewardItemResponse : responses.getContent()) {
      if (rewardItemResponse.getRewardItemType().equals(RewardItemType.PRODUCT) ||
          rewardItemResponse.getRewardItemType().equals(RewardItemType.VOUCHER)) {
        countRewardItems(rewardItemResponse);
      }
    }
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

  public Boolean createRewardItems(RewardItemDto rewardItemDto) {
    RewardItem rewardItem = modelMapper.toRewardItem(rewardItemDto);
    RewardType rewardType = rewardTypeStorage.findById(rewardItemDto.getRewardTypeId());
    if (rewardType == null) {
      throw new ResourceNotFoundException("reward type not found");
    }
    rewardItem.setRewardTypeId(rewardType.getId());
    rewardItem.setRewardItemType(rewardType.getType());
    try {
      self.save(rewardItem);
    } catch (Exception e) {
      log.debug("==============>createRewardItem:DB:Exception:{}", e.getMessage());
    }
    return true;
  }

  public Boolean updateRewardItems(Long id, RewardItemUpdateDto rewardItemUpdateDto) {
    RewardItem rewardItem = rewardItemStorage.findById(id);
    if (rewardItem == null) {
      throw new ResourceNotFoundException("item not found");
    }
    modelMapper.mapRewardItemUpdateDtoToRewardItem(rewardItemUpdateDto, rewardItem);
    try {
      self.save(rewardItem);
    } catch (Exception e) {
      log.debug("==============>updateRewardItem:DB:Exception:{}", e.getMessage());
    }
    return true;
  }

  public RewardItemResponse getRewardItemDetail(Long id) {
    RewardItem rewardItem = rewardItemStorage.findById(id);
    if (rewardItem == null) {
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardItemResponse(rewardItem);
  }

  public List<RewardItemResponse> getRwItemActive() {
    List<RewardItem> rewardItems = rewardItemStorage.findRewardItemByStatus();
    return modelMapper.toRewardItemResponseList(rewardItems);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean updateQuantity(ProcessQuantityDto dto) {
    RewardItem rewardItem = rewardItemStorage.findById(dto.getId());
    if (rewardItem == null) {
      throw new ResourceNotFoundException("Item not found");
    }

    RewardType rewardType = rewardTypeStorage.findById(rewardItem.getRewardTypeId());
    if (rewardType.getType().equals(RewardItemType.PRODUCT) || rewardType.getType().equals(RewardItemType.VOUCHER)) {
      throw new BadRequestException("Can't change quantity of product or voucher");
    }
    if (Objects.equals(dto.getType(), ProcessQuantityDto.ProcessQuantityType.ADD)) {
      rewardItem.addQuantity(dto.getQuantity());
    } else {
      if (rewardItem.getQuantity() < dto.getQuantity()) {
        throw new BadRequestException("Quantity need to be greater than 0");
      }
      rewardItem.minusQuantity(dto.getQuantity());
    }
    rewardItemStorage.save(rewardItem);
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(RewardItem rewardItem) {
    rewardItemStorage.save(rewardItem);
  }

  public void countRewardItems(RewardItemResponse rewardItemResponse) {
    Long quantity;
    if (Objects.equals(rewardItemResponse.getRewardItemType(), RewardItemType.VOUCHER)) {
      quantity = voucherDetailStorage.countVoucherDetailByStoreIdAndStatus(rewardItemResponse.getId(), RewardItemStatus.NEW);
    } else if (Objects.equals(rewardItemResponse.getRewardItemType(), RewardItemType.PRODUCT)) {
      quantity = productDetailStorage.countProductDetailByStoreIdAndStatus(rewardItemResponse.getId(), RewardItemStatus.NEW);
    } else {
      return;
    }
    rewardItemResponse.setQuantity(quantity);
  }
}
