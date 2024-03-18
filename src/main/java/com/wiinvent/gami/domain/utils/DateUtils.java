package com.wiinvent.gami.domain.utils;

import java.time.*;

public class DateUtils {

  public static final String ZONE_UTC = "UTC";

  public static final String ZONE_VN = "Asia/Ho_Chi_Minh";

  public static Long getNowMillisAtUtc() {
    return LocalDateTime.now(ZoneId.of(ZONE_UTC)).toInstant(ZoneOffset.UTC).toEpochMilli();
  }

  public static LocalDateTime getNowDateTimeAtUtc() {
    return LocalDateTime.now(ZoneId.of(ZONE_UTC));
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

  public static LocalDateTime longToLocalDateTime(Long millisAtUtc) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(millisAtUtc), ZoneId.of(ZONE_UTC));
  }

  public static LocalDate longToLocalDate(Long millisAtUtc) {
    return LocalDate.ofInstant(Instant.ofEpochMilli(millisAtUtc), ZoneId.of(ZONE_UTC));
  }

  public static Long timeToLongAtUtc(LocalDateTime dateTimeAtUtc) {
    return ZonedDateTime.of(dateTimeAtUtc, ZoneId.of(ZONE_UTC)).toInstant().toEpochMilli();
  }

  public static Long timeToLongAtVn(LocalDateTime dateTimeAtVn) {
    return ZonedDateTime.of(dateTimeAtVn, ZoneId.of(ZONE_VN)).toInstant().toEpochMilli();
  }

  public static Long getStartOfDay(LocalDate date) {
    LocalDateTime localDateTime = date.atStartOfDay();
    return localDateTime
        .atZone(ZoneId.of(ZONE_VN))
        .withZoneSameInstant(ZoneId.of(ZONE_UTC))
        .toInstant()
        .toEpochMilli();
  }

  public static Long getEndOfDay(LocalDate date) {
    LocalDateTime localDateTime = date.atStartOfDay().plusDays(1).minusSeconds(1);
    return localDateTime
        .atZone(ZoneId.of(ZONE_VN))
        .withZoneSameInstant(ZoneId.of(ZONE_UTC))
        .toInstant()
        .toEpochMilli();
  }

  public static void main(String[] args) {
    System.out.println(timeToLongAtVn(LocalDateTime.now()));
  }
}
