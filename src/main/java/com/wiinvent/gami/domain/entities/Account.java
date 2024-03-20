package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.AccountRole;
import com.wiinvent.gami.domain.entities.type.AccountState;
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
  @Column(name = "id")
  public UUID id;

  @Column(name = "username")
  public String username;

  @Column(name = "password")
  public String password;

  @Column(name = "team_id")
  public UUID teamId;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  public AccountRole role;

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  public AccountState accountState;

}
