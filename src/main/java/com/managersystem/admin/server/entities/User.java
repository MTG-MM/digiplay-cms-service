package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Table( name = "user")
@Entity
public class User extends BaseEntity {

  @Id
  private UUID id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name ="last_name")
  private String lastName;

  @Column(name ="phone_number", nullable = false)
  private String phoneNumber;

  @Column(name ="email", nullable = false)
  private String email;

  @Column(name ="current_address")
  private String currentAddress;

  @Column(name ="display_name")
  private String displayName;

  @Column(name ="birth")
  private String birth;

  @Column(name ="point")
  private Long point = 0L;

  @Column(name ="user_segment_id")
  private Long userSegmentId;

  @Column(name ="invite_code")
  private String inviteCode; // Mã mời của tài khoản bản thân

  @Column(name ="invited_code")
  private String invitedCode; // Mã mời bản thân đã nhập

  @Column(name = "is_email_verify")
  private Boolean isVerifyEmail = false;

  @Column(name = "is_phone_number_verify")
  private Boolean isVerifyPhoneNumber = false;

  public void addPointForUser(long amount){
    point += amount;
  }
  public void minusPointForUser(long amount){
    point = Math.max(0, point - amount);
  }
}
