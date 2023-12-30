package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.type.UserRole;
import com.managersystem.admin.server.stores.base.BaseStorage;
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

  public AccountEntity findByUsername(String username) {
    return accountRepository.findByUsername(username);
  }
}
