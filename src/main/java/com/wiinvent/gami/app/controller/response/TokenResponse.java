package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
  String token;

  AccountRole role;
}
