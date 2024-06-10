package com.wiinvent.gami.domain.service.user;

import com.wiinvent.gami.domain.dto.UserSegmentCreateDto;
import com.wiinvent.gami.domain.dto.UserSegmentUpdateDto;
import com.wiinvent.gami.domain.entities.Achievement;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.user.UserSegment;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.UserSegmentResponse;
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
public class UserSegmentService extends BaseService {
  @Autowired @Lazy private UserSegmentService self;

  public UserSegmentResponse getUserSegmentDetail(long segmentId) {
    UserSegment userSegment = userSegmentStorage.findById(segmentId);
    if (userSegment == null) {
      throw new ResourceNotFoundException(Constants.USER_SEGMENT_NOT_FOUND);
    }
    return modelMapper.toUserSegmentResponse(userSegment);
  }

  public Page<UserSegmentResponse> getPageUserSegment(Long id, String name, Status status, Pageable pageable) {
    Page<UserSegment> userSegments = userSegmentStorage.findAll(id, name, status, pageable);
    return modelMapper.toPageUserSegmentResponse(userSegments);
  }

  public List<UserSegmentResponse> findAllUserSegmentActive() {
    List<UserSegment> userSegments = userSegmentStorage.findAllUserSegmentActive();
    return modelMapper.toListUserSegmentResponse(userSegments);
  }

  public void createUserSegment(UserSegmentCreateDto userSegmentCreateDto) {
    UserSegment userSegment = modelMapper.toUserSegment(userSegmentCreateDto);
    self.save(userSegment);
  }

  public void updateUserSegment(long id, UserSegmentUpdateDto userSegmentUpdateDto) {
    UserSegment userSegment = userSegmentStorage.findById(id);
    if (userSegment == null) {
      throw new ResourceNotFoundException(Constants.USER_SEGMENT_NOT_FOUND);
    }
    modelMapper.mapUserSegmentDtoToUserSegment(userSegmentUpdateDto, userSegment);
    self.save(userSegment);
  }

  public boolean deleteUserSegment(long id) {
    UserSegment userSegment = userSegmentStorage.findById(id);
    if (userSegment == null) {
      throw new ResourceNotFoundException(Constants.USER_SEGMENT_NOT_FOUND);
    }
    userSegment.setStatus(Status.DELETE);
    //save
    try {
      self.save(userSegment);
    } catch (Exception e){
      log.error("==============>deleteUserSegment:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(UserSegment userSegment){
    userSegmentStorage.save(userSegment);
  }
}
