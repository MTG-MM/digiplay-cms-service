package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.AccountRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

  @Schema(description = "Token được trả về")
  private String token;

  @Schema(description = "Vai trò của tài khoản liên quan đến token")
  private AccountRole role;
}
