package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.User;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import com.wiinvent.gami.domain.utils.RemoteCache;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserStorage extends BaseStorage {

  public User findById(UUID userId) {
    return userRepository.findById(userId).orElse(null);
  }

  public void save(User user) {
    userRepository.save(user);
  }


}
