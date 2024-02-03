package com.managersystem.admin.server.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateUtils {

  public static String UTC_ZONE = "UTC";

  public static String ZONE_VN = "Asia/Ho_Chi_Minh";

  public static Long getNowMillisAtUtc() {
    return LocalDateTime.now(ZoneId.of(UTC_ZONE)).toInstant(ZoneOffset.UTC).toEpochMilli();
  }

  public static LocalDateTime getNowDateTimeAtUtc() {
    return LocalDateTime.now(ZoneId.of(UTC_ZONE));
  }

  public static LocalDateTime getNowDateTimeAtVn() {
    return LocalDateTime.now(ZoneId.of(ZONE_VN));
  }

  public static LocalDate getNowDateAtVn() {
    return getNowDateTimeAtVn().toLocalDate();
  }

  public static LocalDate getNowDateAtUtc() {
    return getNowDateTimeAtUtc().toLocalDate();
  }

  public static void main(String[] args) {
    System.out.println(getNowMillisAtUtc());
  }
}
