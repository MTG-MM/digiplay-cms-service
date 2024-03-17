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

  @Column(name = "team_id")
  public Long teamId;
}
