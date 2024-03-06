package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.UserInfoDto;
import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.entities.UserSegment;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService extends BaseService {

  @Autowired UserSegmentService userSegmentService;
  @Transactional(propagation = Propagation.MANDATORY)
  public void createUserInfo(UUID userId, UserInfoDto userInfoDto){
    User user = modelMapper.toUser(userInfoDto);
    user.setId(userId);
    user.setLastLogin(DateUtils.getNowMillisAtUtc());
    userSegmentService.getUserSegment(user);
    userStorage.save(user);
  }

  /**
   * Cong point cho user
   */
  public void addPointForUser(User user, Long amount) {
    user.addPointForUser(amount);
  }
}
