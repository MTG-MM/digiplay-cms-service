package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.AccountRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountDto {

  @NotNull(message = "Không được để trống")
  @Schema(description = "Tên người dùng", example = "nguyenvanA")
  String username;

  @NotNull(message = "Không được để trống")
  @Schema(description = "Mật khẩu", example = "abc123")
  String password;

  @NotNull(message = "Không được để trống")
  @Schema(description = "Vai trò tài khoản", example = "OPERATOR")
  AccountRole role;
}
