package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.UserRole;
import lombok.Data;

@Data
public class AccountDto {

  String username;

  String password;
}
