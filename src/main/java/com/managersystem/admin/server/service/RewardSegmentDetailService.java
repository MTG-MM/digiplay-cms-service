package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailDto;
import com.managersystem.admin.handleRequest.controller.response.RewardSegmentDetailResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardSegmentDetailService extends BaseService {

  public List<RewardSegmentDetailResponse> getAllRewardSegmentDetails(Long id) {
    return modelMapper.toRewardSegmentDetailResponses(rewardSegmentDetailStorage.findByRewardSegmentId(id));
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
