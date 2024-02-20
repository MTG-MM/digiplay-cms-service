package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailUpdateDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailsUpdateDto;
import com.managersystem.admin.handleRequest.controller.response.RewardSegmentDetailResponse;
import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.Constant;
import org.springframework.stereotype.Service;

import java.util.*;
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

  public Boolean updateRewardSegmentDetails(Long id, RewardSegmentDetailUpdateDto rewardSegmentDetailDto) {
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

  public Boolean publishRewardSegmentDetails(Long rewardSegmentId, List<RewardSegmentDetailsUpdateDto> detailsUpdateDtos) {
    List<RewardSegmentDetail> oldSegmentDetails = rewardSegmentDetailStorage.findByRewardSegmentId(rewardSegmentId);
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findByIdIn(rewardSegmentId, detailsUpdateDtos.stream().map(RewardSegmentDetailsUpdateDto::getRewardItemId).toList());
    Map<Long, RewardSegmentDetailsUpdateDto> detailsUpdateDtoMap = detailsUpdateDtos.stream().filter(Objects::nonNull).collect(Collectors.toMap(RewardSegmentDetailsUpdateDto::getRewardItemId, Function.identity()));
    Map<Long, RewardSegmentDetail> segmentDetailMap = rewardSegmentDetails.stream().collect(Collectors.toMap(RewardSegmentDetail::getId, Function.identity()));
    for(RewardSegmentDetail segmentDetail : rewardSegmentDetails){
      RewardSegmentDetailsUpdateDto detailsUpdateDto = detailsUpdateDtoMap.get(segmentDetail.getRewardItemId());
      if(detailsUpdateDto == null){
        throw new ResourceNotFoundException("Item " + segmentDetail.getId() + " not found");
      }

      RewardSegmentDetail rewardSegmentDetail = segmentDetailMap.get(segmentDetail.getRewardItemId());
      if(rewardSegmentDetail == null){
        throw new ResourceNotFoundException("Item " + segmentDetail.getId() + " not found");
      }

      modelMapper.mapRewardSegmentDetailsDtoToRewardSegmentDetail(detailsUpdateDto, rewardSegmentDetail);
    }
    List<RewardSegmentDetail> updateRwSegment = new ArrayList<>(segmentDetailMap.values());
    List<Long> oldIds = oldSegmentDetails.stream().map(RewardSegmentDetail::getId).toList();
    List<Long> updateIds = updateRwSegment.stream().map(RewardSegmentDetail::getId).toList();
    List<Long> removeIds = new ArrayList<>(oldIds);
    removeIds.retainAll(updateIds);
    rewardSegmentDetailStorage.deleteAllById(removeIds);
    rewardSegmentDetailStorage.saveAll(updateRwSegment);
    return true;
  }
}
