package kr.codesqaud.cafe.controller.dto;

public class ApiResponse<T> {
    private final boolean success;

    private final T result;

    public ApiResponse(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(true, result);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }
}
