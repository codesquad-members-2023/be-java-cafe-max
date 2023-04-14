package kr.codesqaud.cafe.dto;

import org.springframework.lang.NonNull;

public class ArticlePostRequest {
    @NonNull
    private String writer;
    @NonNull
    private String title;
    private String contents;

    public ArticlePostRequest(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}

