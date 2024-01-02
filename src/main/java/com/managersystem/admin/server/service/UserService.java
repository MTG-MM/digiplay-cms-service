package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.UserInfoDto;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.UserEntity;
import com.managersystem.admin.server.exception.base.ResourceNotFoundException;
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
    AccountEntity account = accountStorage.findById(userId);
    if(account == null){
      throw new ResourceNotFoundException();
    }
    UserEntity user = modelMapper.toUser(userInfoDto);
    user.setId(userId);
  }
}
