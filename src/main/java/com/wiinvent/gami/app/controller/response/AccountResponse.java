package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.AccountRole;
import com.wiinvent.gami.domain.entities.type.AccountState;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountResponse {

  public UUID id;

  public AccountRole role;

  public AccountState accountState;

  public String username;

  public UUID teamId;
}
