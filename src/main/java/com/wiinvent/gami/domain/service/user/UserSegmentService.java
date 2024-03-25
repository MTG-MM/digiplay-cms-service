package com.wiinvent.gami.domain.service.user;

import com.wiinvent.gami.domain.dto.UserSegmentDto;
import com.wiinvent.gami.domain.entities.user.UserSegment;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.UserSegmentResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserSegmentService extends BaseService {


  public UserSegmentResponse getUserSegmentDetail(long segmentId) {
    UserSegment userSegment = userSegmentStorage.findById(segmentId);
    if (userSegment == null) {
      throw new ResourceNotFoundException(Constant.USER_SEGMENT_NOT_FOUND);
    }
    return modelMapper.toUserSegmentResponse(userSegment);
  }

  public Page<UserSegmentResponse> getPageUserSegment(Pageable pageable) {
    Page<UserSegment> userSegments = userSegmentStorage.findAll(pageable);
    return modelMapper.toPageUserSegmentResponse(userSegments);
  }

  public void createUserSegment(UserSegmentDto userSegmentDto) {
    UserSegment userSegment = modelMapper.toUserSegment(userSegmentDto);
    userSegmentStorage.save(userSegment);
  }

  public void updateUserSegment(long id, UserSegmentDto userSegmentDto) {
    UserSegment userSegment = userSegmentStorage.findById(id);
    if (userSegment == null) {
      throw new ResourceNotFoundException(Constant.USER_SEGMENT_NOT_FOUND);
    }
    modelMapper.mapUserSegmentDtoToUserSegment(userSegmentDto, userSegment);
  }
}
