package com.wiinvent.gami.domain.service.reward;

import com.wiinvent.gami.domain.dto.RewardTypeDto;
import com.wiinvent.gami.domain.dto.RewardTypeUpdateDto;
import com.wiinvent.gami.domain.entities.ChallengeDetail;
import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.RewardItemResponse;
import com.wiinvent.gami.domain.response.RewardTypeResponse;
import com.wiinvent.gami.domain.entities.reward.RewardType;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class RewardTypeService extends BaseService {
  @Autowired
  @Lazy
  private RewardTypeService self;

  public PageResponse<RewardTypeResponse> getAllRewardTypes(String name, Pageable pageable) {
    Page<RewardType> rewardTypes = rewardTypeStorage.findAll(name, pageable);
    return PageResponse.createFrom(modelMapper.toPageRewardTypeResponse(rewardTypes));
  }

  public Long createRewardTypes(RewardTypeDto rewardTypeDto) {
    RewardType rewardType = modelMapper.toRewardType(rewardTypeDto);
    try {
      self.save(rewardType);
    } catch (Exception e) {
      log.debug("==============>createRewardType:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return rewardType.getId();
  }

  public Boolean updateRewardTypes(Long id, RewardTypeUpdateDto rewardTypeDto) {
    RewardType rewardType = rewardTypeStorage.findById(id);
    if (rewardType == null){
      throw new ResourceNotFoundException("item not found");
    }
    modelMapper.mapRewardTypeDtoToRewardType(rewardTypeDto, rewardType);
    try {
      self.save(rewardType);
    } catch (Exception e) {
      log.debug("==============>updateRewardType:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public RewardTypeResponse getRewardTypeDetail(Long id) {
    RewardType rewardType = rewardTypeStorage.findById(id);
    if (rewardType == null){
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardTypeResponse(rewardType);
  }

  public boolean deleteRewardType(Long id) {
    RewardType rewardType = rewardTypeStorage.findById(id);
    if (rewardType == null){
      throw new ResourceNotFoundException("item not found");
    }
    rewardType.setStatus(Status.DELETE);
    try {
      self.save(rewardType);
    } catch (Exception e){
      log.error("==============>deleteRewardType:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public List<RewardTypeResponse> getRwTypeActive() {
    List<RewardType> rewardTypes = rewardTypeStorage.findRewardTypeByStatus();
    return modelMapper.toRewardTypeResponseList(rewardTypes);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(RewardType rewardType) {
    rewardTypeStorage.save(rewardType);
  }
}
