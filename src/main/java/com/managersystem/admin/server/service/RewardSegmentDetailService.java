package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailUpdateDto;
import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailsUpdateDto;
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

import java.util.ArrayList;
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

  public Boolean publishRewardSegmentDetails(List<RewardSegmentDetailsUpdateDto> detailsUpdateDtos) {
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findByIdIn(detailsUpdateDtos.stream().map(RewardSegmentDetailsUpdateDto::getId).toList());
    Map<Long, RewardSegmentDetailsUpdateDto> detailsUpdateDtoMap = detailsUpdateDtos.stream().collect(Collectors.toMap(RewardSegmentDetailsUpdateDto::getId, Function.identity()));
    Map<Long, RewardSegmentDetail> segmentDetailMap = rewardSegmentDetails.stream().collect(Collectors.toMap(RewardSegmentDetail::getId, Function.identity()));
    for(RewardSegmentDetail segmentDetail : rewardSegmentDetails){
      RewardSegmentDetailsUpdateDto detailsUpdateDto = detailsUpdateDtoMap.get(segmentDetail.getId());
      if(detailsUpdateDto == null){
        throw new ResourceNotFoundException("Item " + segmentDetail.getId() + " not found");
      }

      RewardSegmentDetail rewardSegmentDetail = segmentDetailMap.get(segmentDetail.getId());
      if(rewardSegmentDetail == null){
        throw new ResourceNotFoundException("Item " + segmentDetail.getId() + " not found");
      }

      modelMapper.mapRewardSegmentDetailsDtoToRewardSegmentDetail(detailsUpdateDto, rewardSegmentDetail);
    }

    rewardSegmentDetailStorage.saveAll(new ArrayList<>(segmentDetailMap.values()));
    return true;
  }
}
