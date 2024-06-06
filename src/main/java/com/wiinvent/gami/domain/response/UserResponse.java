package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.State;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.UserType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
  private UUID id;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private String email;

  private Long coin = 0L;

  private Long point = 0L;

  private String imageUrl;

  private String displayName;

  private String birth;

  private Long userSegmentId;

//  private String inviteCode; // Mã mời của tài khoản bản thân
//
//  private String invitedCode; // Mã mời bản thân đã nhập

  private Long lastLogin;

//  private Boolean isEmailVerify;

//  private Boolean isPhoneNumberVerify;
  private String gender;

  private Integer level;

  private String levelName;

  private Integer turn;

  private Long createdAt;

  private State state;

  private Status status;

  private Long exp;

  private Double pointBonusRate;

  private Integer ticket;

  private Integer luckyPoint;

  private Integer pointLimit;

  private Long expUpLevel;

  private Long pointUpLevel;

  private List<UserType> userTypes;
}
