package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.app.controller.dto.UserInfoDto;
import com.wiinvent.gami.domain.entities.User;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
