package com.managersystem.admin.server.entities.type;

public enum PeriodLimitType {
  ALL_THE_TIME(30 * 24 * 60 * 60 * 1000L * 12 * 10), //10 nam
  UNLIMITED(-1L),
  DAY(24 * 60 * 60 * 1000L),  // 1 ngay
  WEEK(7 * 24 * 60 * 60 * 1000L),  // 1 tuan
  MONTH(30 * 24 * 60 * 60 * 1000L);  // 1 thang

  private final long millis;

  PeriodLimitType(long millis) {
    this.millis = millis;
  }

  public long getMillis() {
    return millis;
  }

}
