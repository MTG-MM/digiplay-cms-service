package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.State;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

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

  public Long lastLogin;

//  private Boolean isEmailVerify;

//  private Boolean isPhoneNumberVerify;
  private String gender;

  private Integer level = 0;

  private Integer turn;

  private Long rank;

  private Long createdAt;

  private State state;

  private Status status;

  private Long exp;

  private Double pointBonusRate;

  private Integer pointLimit;

  private Long expUpLevel;
}
