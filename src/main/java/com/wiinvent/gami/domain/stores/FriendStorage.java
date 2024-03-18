package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Friend;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FriendStorage extends BaseStorage {

  public void save(Friend userFriend) {
    friendRepository.save(userFriend);
  }

}
