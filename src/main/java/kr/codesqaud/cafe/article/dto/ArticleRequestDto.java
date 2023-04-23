package kr.codesqaud.cafe.article.dto;

import kr.codesqaud.cafe.article.Article;

/**
 * 게시글 DTO 요청 객체
 */
public class ArticleRequestDto {

    private String writer;
    private String title;
    private String contents;

    public Article toEntity(String writer) {
        return new Article.Builder()
                .writer(writer)
                .title(title)
                .contents(contents)
                .build();
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(final String writer) {
        this.writer = writer;
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
