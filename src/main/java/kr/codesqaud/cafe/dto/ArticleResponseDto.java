package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.article.Article;

public class ArticleResponseDto {

    private int index;
    private String userId;
    private String writer;
    private String title;
    private String contents;
    private String date;
    private int replyCount;

    public ArticleResponseDto(Article article) {
        this.index = article.getIndex();
        this.userId = article.getUserId();
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.contents = article.getContents();
        this.date = article.getDate();
    }

    public int getIndex() {
        return index;
    }

    public String getUserId() {
        return userId;
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

    public String getDate() {
        return date;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }
}
