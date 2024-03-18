package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.app.controller.dto.RewardItemStoreCreateDto;
import com.wiinvent.gami.app.controller.dto.RewardItemStoreUpdateDto;
import com.wiinvent.gami.app.controller.response.RewardItemStoreResponse;
import com.wiinvent.gami.app.controller.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.RewardItemStore;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardItemStoreService extends BaseService {
  public RewardItemStoreResponse getRewardItemStoreDetail(Long id) {
    RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(id);
    if (rewardItemStore == null){
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardItemStoreResponse(rewardItemStore);
  }

  public PageResponse<RewardItemStoreResponse> getAllRewardItemStores(Pageable pageable) {
    Page<RewardItemStore> rewardItemStores = rewardItemStoreStorage.findAll(pageable);
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
    if (rewardItemStore == null){
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapRewardItemStoreDtoToRewardItemStore(rewardItemStoreDto, rewardItemStore);
    rewardItemStoreStorage.save(rewardItemStore);
    return true;
  }

  public List<RewardItemStoreResponse> getAllRewardItemStores(StoreType type) {
    return modelMapper.toListRewardItemStoreResponses(rewardItemStoreStorage.findByType(type));
  }
}
