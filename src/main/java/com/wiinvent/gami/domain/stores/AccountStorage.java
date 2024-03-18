package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.UserAccount;
import com.wiinvent.gami.domain.entities.type.UserRole;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountStorage extends BaseStorage {

  public Account findByUsernameAndRole(String username, UserRole userRole) {
    return accountRepository.findByUsernameAndRole(username, userRole);
  }


  public void save(Account account) {
    accountRepository.save(account);
  }

  public Account findById(UUID id) {
    return accountRepository.findById(id).orElse(null);

  }

  public Account findByUsername(String username) {
    return accountRepository.findByUsername(username);
  }
}
