package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.UserInfoDto;
import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService extends BaseService {

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void createUserInfo(UUID userId, UserInfoDto userInfoDto){
    User user = modelMapper.toUser(userInfoDto);
    user.setId(userId);
    userStorage.save(user);
  }

  /**
   * Cong point cho user
   */
  public void addPointForUser(User user, Long amount) {
    user.addPointForUser(amount);
  }
}
