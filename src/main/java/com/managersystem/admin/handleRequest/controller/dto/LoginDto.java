package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.UserRole;
import lombok.Data;

@Data
public class LoginDto {
  private String username;

  private String password;
}
