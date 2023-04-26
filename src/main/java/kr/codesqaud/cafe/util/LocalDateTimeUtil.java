package kr.codesqaud.cafe.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm");

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER);
    }

    public static LocalDateTime toLocalDateTime(String localDateTime) {
        return LocalDateTime.parse(localDateTime, FORMATTER);
    }
}
