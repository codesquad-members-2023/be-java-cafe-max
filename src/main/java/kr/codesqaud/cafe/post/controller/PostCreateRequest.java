package kr.codesqaud.cafe.post.controller;

public class PostCreateRequest {
    private String title;
    private String writer;
    private String contents;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
