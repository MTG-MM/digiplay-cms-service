package com.wiinvent.gami.domain.service.user;

import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService {

  @Autowired UserSegmentService userSegmentService;


  /**
   * Cong point cho user
   */
  public void addPointForUser(User user, Long amount) {
    user.addPointForUser(amount);
  }
}
