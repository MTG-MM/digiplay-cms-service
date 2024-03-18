package com.wiinvent.gami.app.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

  @NotEmpty(message = "Tên người dùng không được trống")
  @Schema(description = "Tên người dùng", example = "nguyenvanA")
  private String username;

  @NotEmpty(message = "Mật khẩu không được trống")
  @Schema(description = "Mật khẩu", example = "abc123")
  private String password;
}
