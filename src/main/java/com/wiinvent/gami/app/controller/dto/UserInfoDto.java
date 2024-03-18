package com.wiinvent.gami.app.controller.dto;

import lombok.Data;

@Data
public class UserInfoDto {
  private String firstName;

  private String lastName;

  private String birth;

  private String currentAddress;

  private String email;

  private String phoneNumber;

  private String inviteCode;
}
