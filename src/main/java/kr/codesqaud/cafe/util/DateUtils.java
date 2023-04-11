package kr.codesqaud.cafe.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String STRING_DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public static String toStringDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(STRING_DATE_FORMAT));
    }
}
