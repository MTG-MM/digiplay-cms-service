package com.managersystem.admin.server.entities.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NewAccountState {
  CREATE_ACCOUNT(0),
  CREATE_PROFILE(1),
  COMPLETE(10);
  public final int level;
}
