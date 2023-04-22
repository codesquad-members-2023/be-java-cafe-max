package kr.codesqaud.cafe.dto.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse {

    private final Integer status;
    private final Map<String, List<String>> message;

    public ErrorResponse(Integer status) {
        this.status = status;
        this.message = new HashMap<>();
    }

    public Integer getStatus() {
        return status;
    }

    public Map<String, List<String>> getMessage() {
        return message;
    }

    public void addMessage(String field, String message) {
        this.message.computeIfAbsent(field, m -> new ArrayList<>())
            .add(message);
    }
}
