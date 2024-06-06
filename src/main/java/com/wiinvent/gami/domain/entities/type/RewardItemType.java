package com.wiinvent.gami.domain.entities.type;

public enum RewardItemType {
  POINT,
  CHARACTER,
  VOUCHER,
  PRODUCT,
  TURN,
  TICKET,
  COIN,
  COLLECTION,
  EXP;

  public static boolean isLimitType(RewardItemType type) {
    return switch (type) {
      case TURN, TICKET, COIN, EXP, POINT, CHARACTER, COLLECTION -> false;
      default -> true;
    };
  }
}
