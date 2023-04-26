package kr.codesqaud.cafe.dto.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ErrorResponse {

    private final HttpStatus status;
    private final Object message;

    public ErrorResponse(HttpStatus status, MethodArgumentNotValidException e) {
        this.status = status;
        this.message = generateMessage(e);
    }

    private Object generateMessage(MethodArgumentNotValidException e) {
        Map<String, List<String>> message = new HashMap<>();
        e.getFieldErrors()
            .forEach(error -> message.computeIfAbsent(error.getField(), m -> new ArrayList<>())
                .add(error.getDefaultMessage()));
        return message;
    }

    public ErrorResponse(HttpStatus status, Object message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatusCode() {
        return status.value();
    }

    public String getStatus() {
        return status.getReasonPhrase();
    }

    public Object getMessage() {
        return message;
    }
}
