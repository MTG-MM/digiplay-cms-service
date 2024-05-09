package com.wiinvent.gami.domain.service.reward;

import com.wiinvent.gami.domain.dto.RewardSegmentDetailDto;
import com.wiinvent.gami.domain.dto.RewardSegmentDetailsUpdateDto;
import com.wiinvent.gami.domain.response.RewardSegmentDetailResponse;
import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.reward.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.type.PeriodLimitType;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constants;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RewardSegmentDetailService extends BaseService {

  //  public List<RewardSegmentDetailResponse> getAllRewardSegmentDetails(Long id) {
//    return modelMapper.toRewardSegmentDetailResponses(rewardSegmentDetailStorage.findByRewardSegmentId(id));
//  }
  public List<RewardSegmentDetailResponse> getAllRewardSegmentDetails(Long id) {
    List<RewardSegmentDetailResponse> responses = modelMapper.toRewardSegmentDetailResponses(rewardSegmentDetailStorage.findByRewardSegmentId(id));
    List<RewardItem> rewardItems = rewardItemStorage.findByIdIn(responses.stream().map(RewardSegmentDetailResponse::getRewardItemId).toList());
    Map<Long, RewardItem> rewardItemMap = rewardItems.stream().collect(Collectors.toMap(RewardItem::getId, Function.identity()));
    responses.forEach(res -> {
      RewardItem rewardItem = rewardItemMap.get(res.getRewardItemId());
      if (rewardItem == null) {
        res.setRewardName(Constants.RESOURCE_IS_DELETED);
      } else {
        res.setRewardName(rewardItem.getRewardName());
        res.setIsLimited(rewardItem.getIsLimited());
        if (Boolean.TRUE.equals(res.getIsLimited())) {
          List<UUID> listIds = remoteCache.rDequeGetAll(cacheKey.getRewardPoolItemIds(res.getRewardSegmentId(), res.getRewardItemId()));
          if (listIds != null && !listIds.isEmpty()) {
            res.setQuantityInPoll(listIds.size());
          }
        }
      }
    });
    return responses;
  }

  public List<RewardSegmentDetailResponse> getAllRwSegmentDetails(Long rwSegmentId) {
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findAll(rwSegmentDetailCondition(rwSegmentId));
    return modelMapper.toRewardSegmentDetailResponses(rewardSegmentDetails);
  }

  public Specification<RewardSegmentDetail> rwSegmentDetailCondition(Long rwSegmentId) {
    return (rwSegmentDetailRoot, query, criteriaBuilder) -> {
      List<Predicate> conditionsList = new ArrayList<>();

      conditionsList.add(criteriaBuilder.equal(rwSegmentDetailRoot.get("rewardSegmentId"), rwSegmentId));

      query.orderBy(criteriaBuilder.asc(rwSegmentDetailRoot.get("position")));
      return criteriaBuilder.and(conditionsList.toArray(new Predicate[0]));
    };
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean updateRewardSegmentDetails(Long rwSegmentId, List<RewardSegmentDetailDto> dtos) {
    if (dtos.stream().noneMatch(RewardSegmentDetailDto::getIsDefault)) {
      throw new BadRequestException("No default Reward Item found");
    }

    if (dtos.stream().filter(RewardSegmentDetailDto::getIsDefault).count() > 1) {
      throw new BadRequestException("Number default is exceed the amount");
    }

    RewardSegmentDetailDto rewardSegmentDetailDtoDefault = dtos.stream().filter(RewardSegmentDetailDto::getIsDefault).toList().get(0);
    if (!rewardSegmentDetailDtoDefault.getPeriodType().equals(PeriodLimitType.UNLIMITED)) {
      throw new BadRequestException("PeriodType of default item must be unlimited");
    }

    Set<Long> positionSet = new HashSet<>();
    for (RewardSegmentDetailDto dto : dtos) {
      if (!positionSet.add(dto.getPosition())) {
        throw new BadRequestException("Position " + " already exist!");
      }
    }
    List<RewardSegmentDetail> rewardSegmentDetails = new ArrayList<>();
    for (RewardSegmentDetailDto rewardSegmentDetailDto : dtos) {
      RewardSegmentDetail rewardSegmentDetail = rewardSegmentDetailStorage.findBySegmentIdAndRwItemId(rwSegmentId, rewardSegmentDetailDto.getRewardItemId());
      if (rewardSegmentDetail == null) {
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
    if (rewardSegmentDetail == null) {
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toRewardSegmentDetailResponse(rewardSegmentDetail);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean publishRewardSegmentDetails(Long rewardSegmentId, List<RewardSegmentDetailsUpdateDto> detailsUpdateDtos) {
    List<RewardSegmentDetail> oldSegmentDetails = rewardSegmentDetailStorage.findByRewardSegmentId(rewardSegmentId);
    List<RewardSegmentDetail> rewardSegmentDetails = rewardSegmentDetailStorage.findByIdIn(rewardSegmentId, detailsUpdateDtos.stream().map(RewardSegmentDetailsUpdateDto::getRewardItemId).toList());
    Map<Long, RewardSegmentDetailsUpdateDto> detailsUpdateDtoMap = detailsUpdateDtos.stream().filter(Objects::nonNull).collect(Collectors.toMap(RewardSegmentDetailsUpdateDto::getRewardItemId, Function.identity()));
    Map<Long, RewardSegmentDetail> segmentDetailMap = rewardSegmentDetails.stream().collect(Collectors.toMap(RewardSegmentDetail::getRewardItemId, Function.identity()));
    for (RewardSegmentDetail segmentDetail : rewardSegmentDetails) {
      RewardSegmentDetailsUpdateDto detailsUpdateDto = detailsUpdateDtoMap.get(segmentDetail.getRewardItemId());
      if (detailsUpdateDto == null) {
        throw new ResourceNotFoundException("Item " + segmentDetail.getId() + " not found");
      }

      RewardSegmentDetail rewardSegmentDetail = segmentDetailMap.get(segmentDetail.getRewardItemId());
      if (rewardSegmentDetail == null) {
        throw new ResourceNotFoundException("Item " + segmentDetail.getId() + " not found");
      }

      modelMapper.mapRewardSegmentDetailsDtoToRewardSegmentDetail(detailsUpdateDto, rewardSegmentDetail);
    }
    List<RewardSegmentDetailsUpdateDto> newRewardSegmentDetail = detailsUpdateDtos.stream()
        .filter(dto -> !segmentDetailMap.containsKey(dto.getRewardItemId())).toList();
    for (RewardSegmentDetailsUpdateDto createDto : newRewardSegmentDetail) {
      RewardSegmentDetail rewardSegmentDetail = modelMapper.toRewardSegmentDetail(createDto);
      rewardSegmentDetail.setRewardSegmentId(rewardSegmentId);
      rewardSegmentDetailStorage.save(rewardSegmentDetail);
    }

    List<RewardSegmentDetail> updateRwSegment = new ArrayList<>(segmentDetailMap.values());
    List<Long> oldIds = oldSegmentDetails.stream().map(RewardSegmentDetail::getId).toList();
    List<Long> updateIds = updateRwSegment.stream().map(RewardSegmentDetail::getId).toList();
    List<Long> removeIds = new ArrayList<>(oldIds);
    removeIds = removeIds.stream().filter(id -> !updateIds.contains(id)).toList();
    rewardSegmentDetailStorage.deleteAllById(removeIds);
    rewardSegmentDetailStorage.saveAll(updateRwSegment);
    return true;
  }
}
