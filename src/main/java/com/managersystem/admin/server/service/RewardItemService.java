package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardItemDto;
import com.managersystem.admin.handleRequest.controller.response.RewardItemResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RewardItemService extends BaseService {

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
}
