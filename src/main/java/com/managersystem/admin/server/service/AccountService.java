package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.handleRequest.controller.dto.LoginDto;
import com.managersystem.admin.handleRequest.controller.dto.UserInfoDto;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService extends BaseService {

  @Autowired
  JwtService jwtService;

  @Autowired UserService userService;

  @Autowired
  @Lazy
  UserSecurityService userSecurityService;

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Account createAccount(AccountDto dto){
    Account account = new Account();
    account.setId(UUID.randomUUID());
    account.setUsername(dto.getUsername());
    account.setPassword(userSecurityService.encode(dto.getPassword()));
    account.setRole(UserRole.OPERATOR);
    accountStorage.save(account);
    return account;
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
    return new TokenResponse(token, account.getRole());
  }

  public boolean initAdminAccount() {
    Account account = new Account();
    account.setId(UUID.randomUUID());
    account.setUsername("MosSystemAdmin");
    account.setPassword(userSecurityService.encode("admin123456"));
    account.setRole(UserRole.ADMIN);
    account.setRole(UserRole.ADMIN);
    accountStorage.save(account);
    return true;
  }

}
