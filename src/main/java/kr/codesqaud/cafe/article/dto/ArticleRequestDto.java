package kr.codesqaud.cafe.article.dto;

import kr.codesqaud.cafe.article.Article;

/**
 * 게시글 DTO 요청 객체
 */
public class ArticleRequestDto {

    private String loginId;
    private String title;
    private String contents;

    public Article toEntity(String loginId) {
        return new Article.Builder()
                .loginId(loginId)
                .title(title)
                .contents(contents)
                .build();
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
