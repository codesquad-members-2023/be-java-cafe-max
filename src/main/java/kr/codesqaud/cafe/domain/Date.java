package kr.codesqaud.cafe.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {

    private String date;

    public Date() {
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
