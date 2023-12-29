package com.managersystem.admin.server.pojo;

import com.managersystem.admin.server.entities.type.NewAccountState;
import com.managersystem.admin.server.entities.type.Rank;
import com.managersystem.admin.server.entities.type.State;
import com.managersystem.admin.server.entities.type.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
public class TokenInfo {
  private UUID id;

  private UserRole role;

  private String username;

  private String groupCode;

  private Rank rank;

  private State state;

  private Long lastLogin;

  private NewAccountState accountState;
}
