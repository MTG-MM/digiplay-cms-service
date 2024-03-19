package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.app.controller.dto.AccountDto;
import com.wiinvent.gami.app.controller.dto.LoginDto;
import com.wiinvent.gami.app.controller.response.AccountResponse;
import com.wiinvent.gami.app.controller.response.TokenResponse;
import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.type.AccountRole;
import com.wiinvent.gami.domain.entities.type.AccountState;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.security.jwt.JwtService;
import com.wiinvent.gami.domain.security.service.UserDetailsImpl;
import com.wiinvent.gami.domain.security.service.UserSecurityService;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService extends BaseService {

  @Autowired JwtService jwtService;

  @Autowired UserService userService;

  @Autowired @Lazy UserSecurityService userSecurityService;

  public TokenResponse login(LoginDto dto){
    Account account = accountStorage.findByUsername(dto.getUsername());
    if(account == null){
      throw new BadRequestException(Constant.USER_NOT_FOUND);
    }
    if(!userSecurityService.decode(dto.getPassword(), account.getPassword())){
      throw new BadRequestException(Constant.USER_NOT_FOUND);
    }
    String token = jwtService.generateToken(account);
    return new TokenResponse(token, account.getRole());
  }

  public void createAccount(UserDetailsImpl userDetails, AccountRole role, AccountDto dto) {
    Account account = accountStorage.findByUsername(userDetails.getUsername());

    if (account == null) {
      throw new BadRequestException(Constant.USER_NOT_FOUND);
    }

    Account newAccount = new Account();
    newAccount.setId(UUID.randomUUID());
    newAccount.setUsername(dto.getUsername());
    newAccount.setAccountState(AccountState.ACTIVE);
    newAccount.setRole(dto.getRole());
    newAccount.setPassword(userSecurityService.encode(dto.getPassword()));

    if (role == AccountRole.ADMIN || role == AccountRole.OPERATOR) {
      newAccount.setTeamId(newAccount.getId());
    } else if (role == AccountRole.PUBLISHER) {
      if (dto.getRole() != AccountRole.READ_PUBLISHER) {
        throw new AccessDeniedException(Constant.INVALID_PERMISSION);
      }
      newAccount.setTeamId(userDetails.getTeamId());
    }

    accountStorage.save(newAccount);
  }

  public boolean initAdminAccount() {
    Account account = new Account();
    account.setId(UUID.fromString("00000000-0000-0000-0000-0000000000"));
    account.setUsername("admin");
    account.setPassword(userSecurityService.encode("admin123456"));
    account.setRole(AccountRole.ADMIN);
    account.setAccountState(AccountState.ACTIVE);
    account.setTeamId(UUID.fromString("00000000-0000-0000-0000-0000000000"));
    accountStorage.save(account);
    return true;
  }

  public Page<AccountResponse> getAllPage(String username, UUID teamId, Pageable pageable) {
    Page<Account> accounts = accountStorage.findPageAccount(username, teamId, pageable);
    return modelMapper.toPageAccountResponse(accounts);
  }
}
