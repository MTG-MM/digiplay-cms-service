package com.managersystem.admin.server.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateUtils {

  public static String UTC_ZONE = "UTC";

  public static Long getNowMillisAtUtc() {
    return LocalDateTime.now(ZoneId.of(UTC_ZONE)).toInstant(ZoneOffset.UTC).toEpochMilli();
  }

  public static void main(String[] args) {
    System.out.println(getNowMillisAtUtc());
  }
}
