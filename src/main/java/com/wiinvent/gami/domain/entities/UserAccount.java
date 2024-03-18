package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.base.BaseEntity;
import com.wiinvent.gami.domain.entities.type.NewAccountState;
import com.wiinvent.gami.domain.entities.type.Rank;
import com.wiinvent.gami.domain.entities.type.State;
import com.wiinvent.gami.domain.entities.type.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "user_account")
@Entity
public class UserAccount extends BaseEntity {

  @Id
  public UUID id;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  public UserRole role;

  @Column(name = "username")
  public String username;

  @Column(name = "password")
  public String password;

  @Column(name = "user_rank")
  @Enumerated(EnumType.STRING)
  public Rank rank;

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  public State state;

  @Column(name = "account_state")
  @Enumerated(EnumType.STRING)
  public NewAccountState accountState;
}
