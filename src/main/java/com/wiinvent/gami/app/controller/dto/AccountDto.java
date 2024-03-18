package com.wiinvent.gami.app.controller.dto;

import com.wiinvent.gami.domain.entities.type.AccountRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountDto {

  @NotNull(message = "Không được để trống")
  String username;

  @NotNull(message = "Không được để trống")
  String password;

  @NotNull(message = "Không được để trống")
  AccountRole accountRole;
}
