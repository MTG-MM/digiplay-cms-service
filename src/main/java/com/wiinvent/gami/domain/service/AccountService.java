package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.app.controller.dto.AccountDto;
import com.wiinvent.gami.app.controller.dto.LoginDto;
import com.wiinvent.gami.app.controller.response.TokenResponse;
import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.type.AccountRole;
import com.wiinvent.gami.domain.entities.type.AccountState;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.security.jwt.JwtService;
import com.wiinvent.gami.domain.security.service.UserSecurityService;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService extends BaseService {

  @Autowired JwtService jwtService;

  @Autowired UserService userService;

  @Autowired @Lazy UserSecurityService userSecurityService;

  public void createAccount(String username, AccountRole role, AccountDto dto){
    Account account = accountStorage.findByUsername(username);
    if(account == null){
      throw new BadRequestException(Constant.USER_NOT_FOUND);
    }

    if(dto.getAccountRole() == AccountRole.PUBLISHER && role != AccountRole.ADMIN){
      throw new BadRequestException(Constant.INVALID_PERMISSION);
    }
    if(dto.getAccountRole() == AccountRole.OPERATOR && role != AccountRole.ADMIN){
      throw new BadRequestException(Constant.INVALID_PERMISSION);
    }

    Account newAccount = new Account();
    newAccount.setId(UUID.randomUUID());
    newAccount.setTeamId(newAccount.getId());
    newAccount.setUsername(dto.getUsername());
    newAccount.setAccountState(AccountState.VERIFY);
    newAccount.setPassword(userSecurityService.encode(dto.getPassword()));
    newAccount.setRole(AccountRole.OPERATOR);
    accountStorage.save(account);
  }

  public TokenResponse login(LoginDto dto){
    Account account = accountStorage.findByUsername(dto.getUsername());
    if(account == null){
      throw new BadRequestException(Constant.INVALID_USERNAME_OR_PASSWORD);
    }
    if(!userSecurityService.decode(dto.getPassword(), account.getPassword())){
      throw new BadRequestException(Constant.INVALID_USERNAME_OR_PASSWORD);
    }
    String token = jwtService.generateToken(account);
    return new TokenResponse(token, account.getRole());
  }

  public boolean initAdminAccount() {
    Account account = new Account();
    account.setUsername("MosSystemAdmin");
    account.setPassword(userSecurityService.encode("admin123456"));
    account.setRole(AccountRole.ADMIN);
    account.setRole(AccountRole.ADMIN);
    accountStorage.save(account);
    return true;
  }

}
