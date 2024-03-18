package com.wiinvent.gami.domain.entities.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {
  BRONZE(100),
  SILVER(500),
  GOLD(2000),
  PLATINUM(10000),
  DIAMOND(20000);

  public final int point;
}
