package com.wiinvent.gami.domain.response;

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

  private String currentAddress;

  private String displayName;

  private String birth;

  private Long userSegmentId;

  private String inviteCode; // Mã mời của tài khoản bản thân

  private String invitedCode; // Mã mời bản thân đã nhập

  public Long lastLogin;

  private Boolean isEmailVerify;

  private Boolean isPhoneNumberVerify;
}
