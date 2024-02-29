package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.Account;
import com.managersystem.admin.server.entities.type.UserRole;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountStorage extends BaseStorage {

  public Account findByUsernameAndRole(String username, UserRole userRole) {
    return accountRepository.findByUsernameAndRole(username, userRole);
  }

  Page<Account> findAll(Pageable pageable){
    return accountRepository.findAll(pageable);
  }

  public void save(Account account) {
    accountRepository.save(account);
  }

  public Account findById(UUID id) {
    String key = cacheKey.getUserById(id);
    Account account = remoteCache.get(key, Account.class);
    if(account == null){
      account = accountRepository.findById(id).orElse(null);
      remoteCache.put(key, account);
    }
    return account;
  }

  public Account findByUsername(String username) {
    String key = cacheKey.getUserByUsername(username);
    Account account = remoteCache.get(key, Account.class);
    if(account == null){
      account = accountRepository.findByUsername(username);
      remoteCache.put(key, account);
    }
    return account;
  }
}
