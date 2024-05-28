package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.user.UserProfile;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserProfileStorage extends BaseStorage {
  public UserProfile findById(UUID id){
    return userProfileRepository.findById(id).orElse(null);
  }
  public List<UserProfile> findAllByIdIn(List<UUID> ids){
    return userProfileRepository.findAllByIdIn(ids);
  }

  public UserProfile findByDisplayName(String displayName){
    return userProfileRepository.findByDisplayName(displayName);
  }

  public UserProfile findByPhoneNumber(String phoneNumber){
    return userProfileRepository.findByPhoneNumber(phoneNumber);
  }
}
