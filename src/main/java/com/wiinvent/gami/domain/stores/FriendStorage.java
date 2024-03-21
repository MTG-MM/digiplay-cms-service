package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Friend;
import org.springframework.stereotype.Component;

@Component
public class FriendStorage extends BaseStorage {

  public void save(Friend userFriend) {
    friendRepository.save(userFriend);
  }

}
