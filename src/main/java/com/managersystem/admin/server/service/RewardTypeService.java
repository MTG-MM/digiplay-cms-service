package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardTypeDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardTypeUpdateDto;
import com.managersystem.admin.handleRequest.controller.response.RewardTypeResponse;
import com.managersystem.admin.server.entities.RewardType;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardTypeService extends BaseService {

  public List<RewardTypeResponse> getAllRewardTypes() {
    List<RewardType> rewardTypes = rewardTypeStorage.findAll();
    return modelMapper.toPageRewardTypeResponse(rewardTypes);
  }

  public boolean createRewardTypes(RewardTypeDto rewardTypeDto) {
    RewardType rewardType = modelMapper.toRewardType(rewardTypeDto);
    rewardTypeStorage.save(rewardType);
    return true;
  }

  public Boolean updateRewardTypes(Long id, RewardTypeUpdateDto rewardTypeDto) {
    RewardType rewardType = rewardTypeStorage.findById(id);
    if (rewardType == null){
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapRewardTypeDtoToRewardType(rewardTypeDto, rewardType);
    rewardTypeStorage.save(rewardType);
    return true;
  }

  public RewardTypeResponse getRewardTypeDetail(Long id) {
    RewardType rewardType = rewardTypeStorage.findById(id);
    if (rewardType == null){
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardTypeResponse(rewardType);
  }
}
