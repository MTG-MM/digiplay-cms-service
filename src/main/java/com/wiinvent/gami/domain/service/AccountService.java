package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.AccountDto;
import com.wiinvent.gami.domain.dto.LoginDto;
import com.wiinvent.gami.domain.response.AccountResponse;
import com.wiinvent.gami.domain.response.TokenResponse;
import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.type.AccountRole;
import com.wiinvent.gami.domain.entities.type.AccountState;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.security.jwt.JwtService;
import com.wiinvent.gami.domain.security.service.UserDetailsImpl;
import com.wiinvent.gami.domain.security.service.UserSecurityService;
import com.wiinvent.gami.domain.service.user.UserService;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AccountService extends BaseService {

  @Autowired JwtService jwtService;

  @Autowired
  UserService userService;

  @Autowired @Lazy UserSecurityService userSecurityService;

  public TokenResponse login(LoginDto dto){
    Account account = accountStorage.findByUsername(dto.getUsername());
    if(account == null){
      throw new BadRequestException(Constants.USER_NOT_FOUND);
    }
    if(!userSecurityService.decode(dto.getPassword(), account.getPassword())){
      throw new BadRequestException(Constants.USER_NOT_FOUND);
    }
    String token = jwtService.generateToken(account);
    return new TokenResponse(token, account.getRole());
  }

  public void createAccount(UserDetailsImpl userDetails, AccountRole role, AccountDto dto) {
    Account account = accountStorage.findByUsername(userDetails.getUsername());

    if (account == null) {
      throw new BadRequestException(Constants.USER_NOT_FOUND);
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
        throw new AccessDeniedException(Constants.INVALID_PERMISSION);
      }
      newAccount.setTeamId(userDetails.getTeamId());
    }

    accountStorage.save(newAccount);
  }

  public AccountResponse getAccountDetail(UUID accountId){
    Account account = accountStorage.findById(accountId);
    if(account == null){
      throw new BadRequestException(Constants.USER_NOT_FOUND);
    }
    return modelMapper.toAccountResponse(account);
  }

  public Boolean delete(String username) {
    if (username == null || username.equals("admin")) {
      return false;
    }

    Account account = accountStorage.findByUserName(username);
    if(account == null){
      throw new BadRequestException(Constants.USER_NOT_FOUND);
    }
    accountStorage.delete(account);
    return true;
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

  public List<AccountResponse> getAllAccountList() {
    List<Account> accounts = accountStorage.findAccountByAccountRole();
    return modelMapper.toListAccountResponse(accounts);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean updateAccount(UUID accountId, AccountDto dto){
    Account account = accountStorage.findById(accountId);
    if(account == null){
      throw new BadRequestException(Constants.USER_NOT_FOUND);
    }

    account.setUsername(dto.getUsername());
    account.setPassword(userSecurityService.encode(dto.getPassword()));
    account.setRole(dto.getRole());
    account.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    if(Objects.nonNull(dto.getAccountState())) account.setAccountState(dto.getAccountState());

    accountStorage.save(account);
    return true;
  }
}
