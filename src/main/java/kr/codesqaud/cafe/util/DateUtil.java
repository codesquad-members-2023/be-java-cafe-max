package kr.codesqaud.cafe.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private String date;

    public DateUtil() {
        this.date = setDate();
    }

    private String setDate(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public String getDate() {
        return date;
    }
}
