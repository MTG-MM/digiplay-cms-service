package com.wiinvent.gami.domain.service.user;

import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.entities.user.UserProfile;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.UserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import com.wiinvent.gami.domain.utils.Helper;
import org.jboss.marshalling.TraceInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    UserResponse userResponse = modelMapper.toUserResponse(user);
    UserProfile userProfile = userProfileStorage.findById(userResponse.getId());
    userResponse.setFirstName(userProfile.getFirstName());
    userResponse.setLastName(userProfile.getLastName());
    userResponse.setEmail(userProfile.getEmail());
    userResponse.setPhoneNumber(userProfile.getPhoneNumber());
    userResponse.setBirth(userProfile.getBirth());
    return userResponse;
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
    List<UUID> userIds = users.stream().map(User::getId).toList();
    List<UserProfile> userProfiles = userProfileStorage.findAllByIdIn(userIds);
    Map<UUID, UserProfile> userProfileMap = userProfiles.stream().collect(Collectors.toMap(UserProfile::getId, Function.identity()));
    List<UserResponse> userResponses = modelMapper.toListUserResponse(users);
    userResponses.forEach(r -> {
      r.setFirstName(userProfileMap.get(r.getId()).getFirstName());
      r.setLastName(userProfileMap.get(r.getId()).getLastName());
      r.setEmail(userProfileMap.get(r.getId()).getEmail());
      r.setPhoneNumber(userProfileMap.get(r.getId()).getPhoneNumber());
      r.setBirth(userProfileMap.get(r.getId()).getBirth());
    });
    return new PageCursorResponse<>(userResponses, limit, next, pre, Constant.CREATED_AT_VARIABLE);
  }
}
