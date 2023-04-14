package kr.codesqaud.cafe.dto;

import org.springframework.lang.NonNull;

public class ArticleResponse {
    @NonNull
    private long articleId;
    @NonNull
    private String writer;
    @NonNull
    private String title;
    private String contents;

    public ArticleResponse(long articleId, String writer, String title, String contents) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public long getArticleId() {
        return articleId;
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
