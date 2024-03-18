package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.type.AccountRole;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountStorage extends BaseStorage {

  public Account findByUsernameAndRole(String username, AccountRole accountRole) {
    return accountRepository.findByUsernameAndRole(username, accountRole);
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
