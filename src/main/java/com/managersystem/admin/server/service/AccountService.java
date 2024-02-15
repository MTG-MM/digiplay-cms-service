package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.handleRequest.controller.dto.LoginDto;
import com.managersystem.admin.handleRequest.controller.response.TokenResponse;
import com.managersystem.admin.server.entities.Account;
import com.managersystem.admin.server.entities.type.NewAccountState;
import com.managersystem.admin.server.entities.type.Rank;
import com.managersystem.admin.server.entities.type.State;
import com.managersystem.admin.server.entities.type.UserRole;
import com.managersystem.admin.server.exception.BadRequestException;
import com.managersystem.admin.server.exception.base.ErrorCode;
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

  @Autowired UserService userService;

  @Autowired
  @Lazy
  UserSecurityService userSecurityService;

  public boolean createAccount(AccountDto dto){
    Account account = new Account();
    account.setId(UUID.randomUUID());
    account.setUsername(dto.getUsername());
    account.setPassword(userSecurityService.encode(dto.getPassword()));
    account.setRank(Rank.BRONZE);
    account.setState(State.NOT_VERIFY);
    account.setAccountState(NewAccountState.CREATE_ACCOUNT);
    account.setLastLogin(DateUtils.getNowMillisAtUtc());
    account.setRole(UserRole.OPERATOR);
    account.setGroupCode("ADMIN");
    account.setCreatedBy(account.getId());
    account.setUpdatedBy(account.getId());
    account.setGroupCode("ADMIN");
    accountStorage.save(account);
    userService.createUserInfo(account.getId(), dto.getUserInfoDto());
    return true;
  }

  public TokenResponse login(LoginDto dto){
    Account account = accountStorage.findByUsername(dto.getUsername());
    if(account == null){
      throw new BadRequestException("Invalid username or password", ErrorCode.INVALID_USERNAME_OR_PASSWORD);
    }
    if(!userSecurityService.decode(dto.getPassword(), account.getPassword())){
      throw new BadRequestException("Invalid username or password", ErrorCode.INVALID_USERNAME_OR_PASSWORD);
    }
    String token = jwtService.generateToken(account);
    return new TokenResponse(token, account.getRole(), account.getAccountState(), account.getState());
  }

  public boolean initAdminAccount() {
    Account account = new Account();
    account.setUsername("MosSystemAdmin");
    account.setPassword(userSecurityService.encode("admin123456"));
    account.setRole(UserRole.ADMIN);
    account.setRank(Rank.DIAMOND);
    account.setState(State.VERIFY);
    account.setAccountState(NewAccountState.COMPLETE);
    account.setLastLogin(DateUtils.getNowMillisAtUtc());
    account.setRole(UserRole.ADMIN);
    account.setGroupCode("ADMIN");
    account.setCreatedBy(account.getId());
    account.setUpdatedBy(account.getId());
    account.setGroupCode("ADMIN");
    accountStorage.save(account);
    return true;
  }
}
