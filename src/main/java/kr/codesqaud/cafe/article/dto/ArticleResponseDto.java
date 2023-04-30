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
    private boolean isDeleted;

    public ArticleResponseDto(long id, String loginId, String title, String contents, boolean isDeleted) {
        this.id = id;
        this.loginId = loginId;
        this.title = title;
        this.contents = contents;
        this.isDeleted = isDeleted;
    }

    public static ArticleResponseDto from(Article article) {
        return new ArticleResponseDto(
                article.getId(), article.getLoginId(), article.getTitle(), article.getContents(), article.getIsDeleted()
        );
    }

    public long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
