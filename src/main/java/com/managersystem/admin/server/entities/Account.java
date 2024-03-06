package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.NewAccountState;
import com.managersystem.admin.server.entities.type.Rank;
import com.managersystem.admin.server.entities.type.State;
import com.managersystem.admin.server.entities.type.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "account")
@Entity
public class Account extends BaseEntity {

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
