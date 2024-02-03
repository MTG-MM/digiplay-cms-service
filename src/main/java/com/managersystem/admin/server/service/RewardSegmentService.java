package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDto;
import com.managersystem.admin.handleRequest.controller.response.RewardSegmentResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.RewardSegment;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RewardSegmentService extends BaseService {

  public PageResponse<RewardSegmentResponse> getAllRewardSegments(Pageable pageable) {
    Page<RewardSegment> rewardSegments = rewardSegmentStorage.findAll(pageable);
    Page<RewardSegmentResponse> responses = modelMapper.toPageRewardSegmentResponse(rewardSegments);
    return PageResponse.createFrom(responses);
  }

  public boolean createRewardSegments(RewardSegmentDto rewardSegmentDto) {
    RewardSegment rewardSegment = modelMapper.toRewardSegment(rewardSegmentDto);
    rewardSegmentStorage.save(rewardSegment);
    return true;
  }

  public Boolean updateRewardSegments(Long id, RewardSegmentDto rewardSegmentDto) {
    RewardSegment rewardSegment = rewardSegmentStorage.findById(id);
    if (rewardSegment == null){
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapRewardSegmentDtoToRewardSegment(rewardSegmentDto, rewardSegment);
    rewardSegmentStorage.save(rewardSegment);
    return true;
  }

  public RewardSegmentResponse getRewardSegmentDetail(Long id) {
    RewardSegment rewardSegment = rewardSegmentStorage.findById(id);
    if (rewardSegment == null){
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardSegmentResponse(rewardSegment);
  }
}
