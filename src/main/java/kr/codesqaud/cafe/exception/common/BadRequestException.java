package kr.codesqaud.cafe.exception.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class BadRequestException extends RuntimeException {

    private final BadRequestExceptionCode badRequestExceptionCode;
    private final String errorCode;
    private final String errorData;

    public BadRequestException(Object errorValue) {
        badRequestExceptionCode = BadRequestExceptionCode
            .getBadException(this.getClass());
        this.errorCode = badRequestExceptionCode.getErrorCode();
        this.errorData = converterJson(errorValue);
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return badRequestExceptionCode.getErrorMessage();
    }

    public String getErrorData() {
        return errorData;
    }

    private String converterJson(Object errorValue) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(errorValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
