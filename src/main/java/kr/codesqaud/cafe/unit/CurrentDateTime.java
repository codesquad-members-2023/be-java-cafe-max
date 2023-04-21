package kr.codesqaud.cafe.unit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDateTime {
    private final LocalDateTime dateTime;

    public CurrentDateTime() {
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return this.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
