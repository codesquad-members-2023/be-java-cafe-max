package kr.codesqaud.cafe.dto;

public class ErrorMessageDTO {
    private final String errorMessage;

    public ErrorMessageDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
