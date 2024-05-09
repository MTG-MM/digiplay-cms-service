package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.AccountState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountStateDto {
  @NotNull
  AccountState accountState;
}
