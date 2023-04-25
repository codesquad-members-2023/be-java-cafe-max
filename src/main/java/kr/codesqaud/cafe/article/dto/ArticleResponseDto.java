package kr.codesqaud.cafe.article.dto;

import kr.codesqaud.cafe.article.Article;

/**
 * 게시글 DTO 객체
 */
public class ArticleResponseDto {

    private long id;
    private String loginId;
    private String title;
    private String contents;

    public ArticleResponseDto fromEntity(Article article) {
        id = article.getId();
        loginId = article.getLoginId();
        title = article.getTitle();
        contents = article.getContents();
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(final String loginId) {
        this.loginId = loginId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(final String contents) {
        this.contents = contents;
    }
}
