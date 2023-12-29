package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.NewAccountState;
import com.managersystem.admin.server.entities.type.Rank;
import com.managersystem.admin.server.entities.type.State;
import com.managersystem.admin.server.entities.type.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(schema = "prod", name = "account")
@Entity
public class AccountEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "group_code")
  private String groupCode;

  @Column(name = "rank")
  @Enumerated(EnumType.STRING)
  private Rank rank;

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  private State state;

  @Column(name = "last_login")
  private Long lastLogin;

  @Column(name = "account_state")
  @Enumerated(EnumType.STRING)
  private NewAccountState accountState;
}
