package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.handleRequest.controller.dto.LoginDto;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.type.NewAccountState;
import com.managersystem.admin.server.entities.type.Rank;
import com.managersystem.admin.server.entities.type.State;
import com.managersystem.admin.server.entities.type.UserRole;
import com.managersystem.admin.server.exception.BadRequestException;
import com.managersystem.admin.server.security.JwtService;
import com.managersystem.admin.server.security.UserSecurityService;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService extends BaseService {

  @Autowired
  JwtService jwtService;


  @Autowired
  @Lazy
  UserSecurityService userSecurityService;

  public boolean createAccount(AccountDto dto){
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setId(UUID.randomUUID());
    accountEntity.setUsername(dto.getUsername());
    accountEntity.setPassword(userSecurityService.encode(dto.getPassword()));
    accountEntity.setRank(Rank.BRONZE);
    accountEntity.setState(State.NOT_VERIFY);
    accountEntity.setAccountState(NewAccountState.CREATE_ACCOUNT);
    accountEntity.setLastLogin(DateUtils.getNowMillisAtUtc());
    accountEntity.setRole(UserRole.OPERATOR);
    accountEntity.setGroupCode("ADMIN");
    accountEntity.setCreatedBy(accountEntity.getId());
    accountEntity.setUpdatedBy(accountEntity.getId());
    accountEntity.setGroupCode("ADMIN");
    accountStorage.save(accountEntity);
    return true;
  }

  public String login(LoginDto dto){
    AccountEntity accountEntity = accountStorage.findByUsernameAndRole(dto.getUsername(), dto.getUserRole());
    if(accountEntity == null){
      throw new BadRequestException("User not exists");
    }
    if(!userSecurityService.decode(dto.getPassword(), accountEntity.getPassword())){
      throw new BadRequestException("User not exists");
    }
    return jwtService.generateToken(accountEntity);
  }

  public boolean initAdminAccount() {
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setUsername("MosSystemAdmin");
    accountEntity.setPassword(userSecurityService.encode("admin123456"));
    accountEntity.setRole(UserRole.ADMIN);
    accountEntity.setRank(Rank.DIAMOND);
    accountEntity.setState(State.VERIFY);
    accountEntity.setAccountState(NewAccountState.COMPLETE);
    accountEntity.setLastLogin(DateUtils.getNowMillisAtUtc());
    accountEntity.setRole(UserRole.ADMIN);
    accountEntity.setGroupCode("ADMIN");
    accountEntity.setCreatedBy(accountEntity.getId());
    accountEntity.setUpdatedBy(accountEntity.getId());
    accountEntity.setGroupCode("ADMIN");
    accountStorage.save(accountEntity);
    return true;
  }
}
