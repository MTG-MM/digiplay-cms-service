package com.wiinvent.gami.domain.service.reward;

import com.google.common.collect.Lists;
import com.wiinvent.gami.domain.dto.RewardItemStoreCreateDto;
import com.wiinvent.gami.domain.dto.RewardItemStoreUpdateDto;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.RewardItemStoreResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.reward.RewardItemStore;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.BaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class RewardItemStoreService extends BaseService {
  public RewardItemStoreResponse getRewardItemStoreDetail(Long id) {
    RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(id);
    if (rewardItemStore == null) {
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardItemStoreResponse(rewardItemStore);
  }

  public PageResponse<RewardItemStoreResponse> getAllRewardItemStores(StoreType type, String name, Pageable pageable) {
    Page<RewardItemStore> rewardItemStores = rewardItemStoreStorage.findAll(type, name, pageable);
    Page<RewardItemStoreResponse> responses = modelMapper.toPageRewardItemStoreResponse(rewardItemStores);
    return PageResponse.createFrom(responses);
  }

  public Boolean createRewardItemStores(RewardItemStoreCreateDto rewardItemStoreDto) {
    RewardItemStore rewardItemStore = modelMapper.toRewardItemStore(rewardItemStoreDto);
    rewardItemStoreStorage.save(rewardItemStore);
    return true;
  }

  public Boolean updateRewardItemStores(Long id, RewardItemStoreUpdateDto rewardItemStoreDto) {
    RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(id);
    if (rewardItemStore == null) {
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapRewardItemStoreDtoToRewardItemStore(rewardItemStoreDto, rewardItemStore);
    rewardItemStoreStorage.save(rewardItemStore);
    try {
      remoteCache.deleteKey(cacheKey.genRewardItemStoreById(rewardItemStore.getId()));
    } catch (Exception e) {
      log.debug("==============>updateRewardItemStores:Cache:Exception:{}", e.getMessage());
    }
    return true;
  }

  public List<RewardItemStoreResponse> getAllRewardItemStores(StoreType type) {
    return modelMapper.toListRewardItemStoreResponses(rewardItemStoreStorage.findByType(type));
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void updateVoucherAmount(Long id) {
    RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(id);
    if (rewardItemStore == null) {
      throw new ResourceNotFoundException("Not found with Voucher with id " + id);
    }
    Long totalQuantity;
    Long quantity;
    if (Objects.equals(rewardItemStore.getType(), StoreType.VOUCHER)) {
      totalQuantity = voucherDetailStorage.countVoucherDetailByStoreId(id);
      quantity = voucherDetailStorage.countVoucherDetailByStoreIdAndStatus(id, RewardItemStatus.NEW);
    } else if (Objects.equals(rewardItemStore.getType(), StoreType.PRODUCT)) {
      totalQuantity = productDetailStorage.countProductDetailByStoreId(id);
      quantity = productDetailStorage.countProductDetailByStoreIdAndStatus(id, RewardItemStatus.NEW);
    } else {
      return;
    }
    rewardItemStore.setTotalQuantity(totalQuantity);
    rewardItemStore.setQuantity(quantity);
    rewardItemStoreStorage.save(rewardItemStore);
  }

}
