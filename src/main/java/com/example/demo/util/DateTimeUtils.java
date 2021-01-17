package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
  public static LocalDateTime getStartOfDateTime(LocalDate startDate) {
    return startDate == null ? null : startDate.atStartOfDay();
  }

  public static LocalDateTime getEndOfDateTime(LocalDate endDate) {
    return endDate == null ? null : LocalDateTime.of(endDate, LocalTime.of(23, 59, 59, 999));
  }

  public static String formatDate(LocalDateTime dateTime, String format) {
    return dateTime.format(DateTimeFormatter.ofPattern(format));
  }

  public static LocalDateTime parseDate(String dateTime, String format) {
    if (StringUtils.isEmpty(dateTime)) {
      return null;
    }
    return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format));
  }

  public static LocalDateTime parseDate(String dateTime) {
   return DateTimeUtils.parseDate(dateTime, Constants.DEFAULT_DATETIME_FORMAT);
  }
}
