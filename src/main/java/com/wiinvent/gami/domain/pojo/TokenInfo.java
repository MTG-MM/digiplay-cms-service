package com.wiinvent.gami.domain.pojo;

import com.wiinvent.gami.domain.entities.type.AccountRole;
import lombok.Data;

import java.util.UUID;

@Data
public class TokenInfo {
  private UUID id;

  private AccountRole role;

  private String username;

  private UUID teamId;

}
