package com.wiinvent.gami.domain.service.reward;

import com.wiinvent.gami.domain.dto.RewardSegmentDto;
import com.wiinvent.gami.domain.response.RewardSegmentResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.reward.RewardSegment;
import com.wiinvent.gami.domain.entities.reward.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.type.PeriodLimitType;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.DateUtils;
import jakarta.persistence.RollbackException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
public class RewardSegmentService extends BaseService {

  public PageResponse<RewardSegmentResponse> getAllRewardSegments(Pageable pageable) {
    Page<RewardSegment> rewardSegments = rewardSegmentStorage.findAll(pageable);
    Page<RewardSegmentResponse> responses = modelMapper.toPageRewardSegmentResponse(rewardSegments);
    return PageResponse.createFrom(responses);
  }

  public Long createRewardSegments(RewardSegmentDto rewardSegmentDto) {
    RewardSegment rewardSegment = modelMapper.toRewardSegment(rewardSegmentDto);
    rewardSegmentStorage.save(rewardSegment);
    return rewardSegment.getId();
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

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean chooseRwItem(Long rewardSegmentId, List<Long> ids){
    List<RewardItem> rewardItems = rewardItemStorage.findRewardItemByIdIn(ids);
    Map<Long, RewardItem> rwItemMap = rewardItems.stream().collect(Collectors.toMap(RewardItem::getId, Function.identity()));

    try{
      List<RewardSegmentDetail> rewardSegmentDetails = new ArrayList<>();
      List<RewardSegmentDetail> rewardSegmentDetailList = rewardSegmentDetailStorage.findByRewardSegmentId(rewardSegmentId);

      // delete uncheck reward
      List<RewardSegmentDetail> rewardSegmentDetailsRemove = rewardSegmentDetailList.stream()
          .filter(rw -> !ids.contains(rw.getRewardItemId()))
          .toList();
      rewardSegmentDetailStorage.deleteList(rewardSegmentDetailsRemove);

      for (Long id : ids) {
        RewardSegmentDetail rewardSegmentDetail = rewardSegmentDetailStorage.findBySegmentIdAndRwItemId(rewardSegmentId, id);

        // check whether checked reward was added to the db or not
        if (rewardSegmentDetail == null) {
          RewardItem rewardItem = rwItemMap.get(id);
          if (rewardItem == null) {
            throw new ResourceNotFoundException();
          }
          RewardSegmentDetail rewardSegmentDetailNew = new RewardSegmentDetail();
          rewardSegmentDetailNew.setRewardItemId(id);
          rewardSegmentDetailNew.setRewardSegmentId(rewardSegmentId);
          rewardSegmentDetailNew.setIsDefault(false);
          rewardSegmentDetailNew.setCreatedAt(DateUtils.getNowMillisAtUtc());
          rewardSegmentDetails.add(rewardSegmentDetailNew);

        } else {
          rewardSegmentDetail.setUpdatedAt(DateUtils.getNowMillisAtUtc());
          rewardSegmentDetails.add(rewardSegmentDetail);
        }
      }

      if(rewardSegmentDetails.stream().filter(RewardSegmentDetail::getIsDefault).toList().isEmpty()){
        rewardSegmentDetails.get(0).setIsDefault(true);
        rewardSegmentDetails.get(0).setPeriodType(PeriodLimitType.UNLIMITED);
      }
      rewardSegmentDetailStorage.saveAll(rewardSegmentDetails);

    } catch (Exception e) {
      log.error("===> Rollback create rw segment" + e);
      throw new RollbackException(e.getMessage());
    }
    return true;
  }
}
