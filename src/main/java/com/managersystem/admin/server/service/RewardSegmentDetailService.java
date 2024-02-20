package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailDto;
import com.managersystem.admin.handleRequest.controller.response.RewardSegmentDetailResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RewardSegmentDetailService extends BaseService {

  public List<RewardSegmentDetailResponse> getAllRewardSegmentDetails(Long id) {
    List<RewardSegmentDetailResponse> responses = modelMapper.toRewardSegmentDetailResponses(rewardSegmentDetailStorage.findByRewardSegmentId(id));
    List<RewardItem> rewardItems = rewardItemStorage.findByIdIn(responses.stream().map(RewardSegmentDetailResponse::getRewardItemId).toList());
    Map<Long, RewardItem> rewardItemMap = rewardItems.stream().collect(Collectors.toMap(RewardItem::getId, Function.identity()));
    responses.forEach(res -> {
      RewardItem rewardItem = rewardItemMap.get(res.getRewardItemId());
      if(rewardItem == null){
        res.setRewardName(Constant.RESOURCE_IS_DELETED);
      }else{
        res.setRewardName(rewardItem.getRewardName());
        res.setIsLimited(rewardItem.getIsLimited());
        if(res.getIsLimited()){
          List<UUID> listIds = remoteCache.rDequeGetAll(cacheKey.getRewardPoolItemIds(res.getRewardSegmentId(), res.getRewardItemId()));
          if(listIds != null && !listIds.isEmpty()){
            res.setQuantityInPoll(listIds.size());
          }
        }
      }
    });
    return responses;
  }

  public boolean createRewardSegmentDetails(RewardSegmentDetailDto rewardSegmentDetailDto) {
    RewardSegmentDetail rewardSegmentDetail = modelMapper.toRewardSegmentDetail(rewardSegmentDetailDto);
    rewardSegmentDetailStorage.save(rewardSegmentDetail);
    return true;
  }

  public Boolean updateRewardSegmentDetails(Long id, RewardSegmentDetailDto rewardSegmentDetailDto) {
    RewardSegmentDetail rewardSegmentDetail = rewardSegmentDetailStorage.findById(id);
    if (rewardSegmentDetail == null){
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapRewardSegmentDetailDtoToRewardSegmentDetail(rewardSegmentDetailDto, rewardSegmentDetail);
    rewardSegmentDetailStorage.save(rewardSegmentDetail);
    return true;
  }

  public RewardSegmentDetailResponse getRewardSegmentDetailDetail(Long id) {
    RewardSegmentDetail rewardSegmentDetail = rewardSegmentDetailStorage.findById(id);
    if (rewardSegmentDetail == null){
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardSegmentDetailResponse(rewardSegmentDetail);
  }
}
