package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.user.UserAccount;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserAccountStorage extends BaseStorage {
  public List<UserAccount> findUserAccountsByIdIn(List<UUID> ids){
    return userAccountRepository.findUserAccountsByIdIn(ids);
  }

  public UserAccount findById(UUID id){
    return userAccountRepository.findById(id).orElse(null);
  }
}