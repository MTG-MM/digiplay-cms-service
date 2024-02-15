package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.stores.base.BaseStorage;
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
