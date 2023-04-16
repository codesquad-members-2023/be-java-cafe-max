package kr.codesqaud.cafe.dto.error;

public class ErrorDto {
    private final int errorStatus;
    private final String errorMessage;

    public ErrorDto(int errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
