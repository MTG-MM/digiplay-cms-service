package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.entities.user.UserSegment;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSegmentService extends BaseService {

  public UserSegment getUserSegment(User user){
    if(user.getUserSegmentId() != null){
      return userSegmentStorage.findById(user.getUserSegmentId());
    }
    List<UserSegment> userSegments = userSegmentStorage.findByIsDefault(true);
    if(!userSegments.isEmpty()){
      user.setUserSegmentId(userSegments.get(0).getId());
      return userSegments.get(0);
    }else{
      throw new ResourceNotFoundException(Constant.INVALID_RANK_OF_USER);
    }
  }
}
