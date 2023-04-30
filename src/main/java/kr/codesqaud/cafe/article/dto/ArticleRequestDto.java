package kr.codesqaud.cafe.article.dto;

import kr.codesqaud.cafe.article.Article;

/**
 * 게시글 DTO 요청 객체
 */
public class ArticleRequestDto {

    private final String title;
    private final String contents;

    public ArticleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity(String loginId) {
        return new Article.Builder()
                .loginId(loginId)
                .title(title)
                .contents(contents)
                .build();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
