package com.wiinvent.gami.domain.entities.user;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.utils.Converter.AesConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Table( name = "user_profile")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile extends BaseEntity {
  @Id
  @Column(name = "id", columnDefinition = "binary(16)")
  private UUID id;

  @Column(name = "username")
  private String userName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name ="last_name")
  private String lastName;

  @Column(name ="phone_number")
  @Convert(converter = AesConverter.class)
  private String phoneNumber;

  @Column(name ="email")
  @Convert(converter = AesConverter.class)
  private String email;

  @Column(name ="birth")
  private String birth;

  @Column(name ="image_url")
  private String imageUrl;

  @Column(name = "display_name")
  private String displayName;
}
