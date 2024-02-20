package com.managersystem.admin.server.service;

import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.entities.UserSegment;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.Constant;
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
