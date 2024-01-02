package com.managersystem.admin.handleRequest.controller.response;

import com.managersystem.admin.server.entities.type.NewAccountState;
import com.managersystem.admin.server.entities.type.State;
import com.managersystem.admin.server.entities.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
  String token;

  UserRole role;

  NewAccountState accountState;

  State state;
}
