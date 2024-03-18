package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.UserNotify;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
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
