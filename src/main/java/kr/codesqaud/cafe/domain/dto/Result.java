package kr.codesqaud.cafe.domain.dto;

public class Result {
    private String message;

    public Result(String message) {
        this.message = message;
    }

    public static Result ok() {
        return new Result("Okay");
    }

    public static Result fail(String message) {
        return new Result(message);
    }

    public String getMessage() {
        return message;
    }
}
