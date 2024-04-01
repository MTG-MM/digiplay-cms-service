package com.wiinvent.gami.domain.service.user;

import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.UserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserService extends BaseService {

  @Autowired
  UserSegmentService userSegmentService;


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

  public UserResponse getUserDetail(UUID userId){
    User user = userStorage.findById(userId);
    if (user == null) {
      throw new ResourceNotFoundException("User id not found");
    }
    return modelMapper.toUserResponse(user);
  }

  public PageCursorResponse<UserResponse> getPageUser
      (UUID userId, String phoneNumber, Long next, Long pre, Integer limit, LocalDate startDate, LocalDate endDate) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    List<User> users = new ArrayList<>();
    CursorType type = CursorType.FIRST;
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      users = userStorage.findAllUser(userId, phoneNumber, next, pre, limit, startDateLong, endDateLong, type);
    } else if (pre == null) {
      type = CursorType.NEXT;
      pre = 0L;
      users = userStorage.findAllUser(userId, phoneNumber, next, pre, limit, startDateLong, endDateLong, type);
    }else if(next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      users = userStorage.findAllUser(userId, phoneNumber, next, pre, limit, startDateLong, endDateLong, type);
      users = users.stream().sorted(Comparator.comparingLong(User::getCreatedAt).reversed()).toList();
    }
    List<UserResponse> userResponses = modelMapper.toListUserResponse(users);
    return new PageCursorResponse<>(userResponses, limit, next, pre, Constant.CREATED_AT_VARIABLE);
  }
}
