package com.wiinvent.gami.domain.service.user;

import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.response.UserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService extends BaseService {

  @Autowired UserSegmentService userSegmentService;


  /**
   * Cong point cho user
   */
  public void addPointForUser(User user, Long amount) {
    user.addPointForUser(amount);
  }

  public PageCursorResponse<UserResponse> getAllUsers(UUID userId, String phoneNumber, Long next, Long pre, int limit) {
    List<User> users = userStorage.findAll(userId, phoneNumber, next, pre, limit);
    List<UserResponse> responses = modelMapper.toListUserResponse(users);
    return new PageCursorResponse<>(responses, limit, next, pre, Constant.CREATED_AT_VARIABLE);
  }
}
