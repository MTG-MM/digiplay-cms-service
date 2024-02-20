package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.RewardSegmentDetailDto;
import com.managersystem.admin.handleRequest.controller.response.RewardSegmentDetailResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.entities.type.PeriodLimitType;
import com.managersystem.admin.server.exception.BadRequestException;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RewardSegmentDetailService extends BaseService {

//  public List<RewardSegmentDetailResponse> getAllRewardSegmentDetails(Long id) {
//    return modelMapper.toRewardSegmentDetailResponses(rewardSegmentDetailStorage.findByRewardSegmentId(id));
//  }

  public List<RewardSegmentDetailResponse> getAllRewardSegmentDetails(Long rwSegmentId){
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findAll(rwSegmentDetailCondition(rwSegmentId));
    return modelMapper.toRewardSegmentDetailResponses(rewardSegmentDetails);
  }

  public Specification<RewardSegmentDetail> rwSegmentDetailCondition(
      Long rwSegmentId) {
    return (rwSegmentDetailRoot, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      conditionsList.add(criteriaBuilder.equal(rwSegmentDetailRoot.get("rewardSegmentId"), rwSegmentId));

      query.orderBy(criteriaBuilder.asc(rwSegmentDetailRoot.get("position")));
      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }

  public Boolean updateRewardSegmentDetails(Long rwSegmentId, List<RewardSegmentDetailDto> dtos){
    if (dtos.stream().noneMatch(RewardSegmentDetailDto::getIsDefault)) {
      throw new BadRequestException("No default Reward Item found");
    }

    if(dtos.stream().filter(RewardSegmentDetailDto::getIsDefault).count() > 1){
      throw new BadRequestException("Number default is exceed the amount");
    }

    RewardSegmentDetailDto rewardSegmentDetailDtoDefault = dtos.stream().filter(RewardSegmentDetailDto::getIsDefault).toList().get(0);
    if(!rewardSegmentDetailDtoDefault.getPeriodType().equals(PeriodLimitType.UNLIMITED)){
      throw new BadRequestException("PeriodType of default item must be unlimited");
    }

    Set<Long> positionSet = new HashSet<>();
    for (RewardSegmentDetailDto dto : dtos) {
      if (!positionSet.add(dto.getPosition())) {
        throw new BadRequestException("Position " + " already exist!");
      }
    }
    List<RewardSegmentDetail> rewardSegmentDetails  = new ArrayList<>();
    for (RewardSegmentDetailDto rewardSegmentDetailDto : dtos) {
      RewardSegmentDetail rewardSegmentDetail = rewardSegmentDetailStorage.findBySegmentIdAndRwItemId(rwSegmentId, rewardSegmentDetailDto.getRewardItemId());
      if (rewardSegmentDetail == null){
        throw new ResourceNotFoundException("item not found");
      }
      modelMapper.mapRewardSegmentDetailDtoToRewardSegmentDetail(rewardSegmentDetailDto, rewardSegmentDetail);
      rewardSegmentDetails.add(rewardSegmentDetail);
    }
    rewardSegmentDetailStorage.saveAll(rewardSegmentDetails);
    return true;
  }

  public boolean createRewardSegmentDetails(RewardSegmentDetailDto rewardSegmentDetailDto) {
    RewardSegmentDetail rewardSegmentDetail = modelMapper.toRewardSegmentDetail(rewardSegmentDetailDto);
    rewardSegmentDetailStorage.save(rewardSegmentDetail);
    return true;
  }

//  public Boolean updateRewardSegmentDetails(Long id, RewardSegmentDetailDto rewardSegmentDetailDto) {
//    RewardSegmentDetail rewardSegmentDetail = rewardSegmentDetailStorage.findById(id);
//    if (rewardSegmentDetail == null){
//      throw new ResourceNotFoundException("item not found");
//    }
//
//    modelMapper.mapRewardSegmentDetailDtoToRewardSegmentDetail(rewardSegmentDetailDto, rewardSegmentDetail);
//    rewardSegmentDetailStorage.save(rewardSegmentDetail);
//    return true;
//  }

  public RewardSegmentDetailResponse getRewardSegmentDetailDetail(Long id) {
    RewardSegmentDetail rewardSegmentDetail = rewardSegmentDetailStorage.findById(id);
    if (rewardSegmentDetail == null){
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardSegmentDetailResponse(rewardSegmentDetail);
  }
}
