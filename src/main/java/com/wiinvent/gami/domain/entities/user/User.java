package com.wiinvent.gami.domain.entities.user;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Table( name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

  @Id
  @Column(name = "id", columnDefinition = "binary(16)")
  private UUID id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name ="last_name")
  private String lastName;

  @Column(name ="phone_number")
  private String phoneNumber;

  @Column(name ="email")
  private String email;

  @Column(name = "coin")
  private Long coin = 0L;

  @Column(name ="point")
  private Long point = 0L;

  @Column(name ="image_url")
  private String imageUrl;

  @Column(name ="display_name")
  private String displayName;

  @Column(name ="birth")
  private String birth;

  @Column(name ="user_segment_id")
  private Long userSegmentId;

  @Column(name = "last_login")
  public Long lastLogin;

  @Column(name = "exp")
  private Long exp;

  @Size(max = 50)
  @Column(name = "level", length = 50)
  private String level;


  public void addPointForUser(long amount){
    point += amount;
  }
  public void minusPointForUser(long amount){
    point = Math.max(0, point - amount);
  }
}
