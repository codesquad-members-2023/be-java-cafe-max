package kr.codesqaud.cafe.errors.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

public class ErrorResponse {

    private final String name;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    @JsonInclude(value = Include.NON_EMPTY)
    private final List<ValidationError> errors;

    public ErrorResponse() {
        this(null, null, null, null);
    }

    public ErrorResponse(String name, HttpStatus httpStatus, String errorMessage,
        List<ValidationError> errors) {
        this.name = name;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

    public String getName() {
        return name;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorResponse)) {
            return false;
        }
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(name, that.name) && getHttpStatus() == that.getHttpStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getHttpStatus());
    }

    @Override
    public String toString() {
        return String.format("ErrorResponse(%s, %s, %s, %s)", name, httpStatus, errorMessage,
            errors);
    }

    public static class ValidationError {

        private final String field;
        private final String message;

        public ValidationError() {
            this(null, null);
        }

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public static ValidationError of(FieldError fieldError) {
            return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ValidationError)) {
                return false;
            }
            ValidationError that = (ValidationError) o;
            return Objects.equals(getField(), that.getField()) && Objects.equals(
                getMessage(), that.getMessage());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getField(), getMessage());
        }

        @Override
        public String toString() {
            return String.format("ValidationError(%s, %s)", field, message);
        }
    }
}
