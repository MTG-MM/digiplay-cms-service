package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.user.UserNotify;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserNotifyStorage extends BaseStorage {


  public void save(UserNotify notification) {
    userNotifyRepository.save(notification);
  }

  public void saveAll(List<UserNotify> userNotifies) {
    userNotifyRepository.saveAll(userNotifies);
  }
}
