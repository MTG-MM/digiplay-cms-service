package com.managersystem.admin.server.stores.user;

import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.type.UserRole;
import com.managersystem.admin.server.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountStorage extends BaseStorage {

  public AccountEntity findByUsernameAndRole(String username, UserRole userRole) {
    return accountRepository.findByUsernameAndRole(username, userRole);
  }


  public void save(AccountEntity account) {
    accountRepository.save(account);
  }

  public AccountEntity findById(UUID id) {
    return accountRepository.findById(id).orElse(null);
  }
}
