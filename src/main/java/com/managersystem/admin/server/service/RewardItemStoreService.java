package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardItemStoreCreateDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardItemStoreUpdateDto;
import com.managersystem.admin.handleRequest.controller.response.RewardItemStoreResponse;
import com.managersystem.admin.handleRequest.controller.response.RewardItemStoreResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.RewardItemStore;
import com.managersystem.admin.server.entities.type.StoreType;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
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
