package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountDto {

  @NotNull(message = "Không được để trống")
  String username;

  @NotNull
  String password;
}
