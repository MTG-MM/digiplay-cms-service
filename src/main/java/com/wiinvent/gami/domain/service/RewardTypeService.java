package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.RewardTypeDto;
import com.wiinvent.gami.domain.dto.RewardTypeUpdateDto;
import com.wiinvent.gami.domain.response.RewardTypeResponse;
import com.wiinvent.gami.domain.entities.RewardType;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardTypeService extends BaseService {

  public List<RewardTypeResponse> getAllRewardTypes() {
    List<RewardType> rewardTypes = rewardTypeStorage.findAll();
    return modelMapper.toPageRewardTypeResponse(rewardTypes);
  }

  public Long createRewardTypes(RewardTypeDto rewardTypeDto) {
    RewardType rewardType = modelMapper.toRewardType(rewardTypeDto);
    rewardTypeStorage.save(rewardType);
    return rewardType.getId();
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
