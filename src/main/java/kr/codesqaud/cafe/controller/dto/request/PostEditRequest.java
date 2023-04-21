package kr.codesqaud.cafe.controller.dto.request;

public class PostEditRequest {
    private final String newTitle;
    private final String newContent;

    public PostEditRequest(String newTitle, String newContent) {
        this.newTitle = newTitle;
        this.newContent = newContent;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String getNewContent() {
        return newContent;
    }
}
