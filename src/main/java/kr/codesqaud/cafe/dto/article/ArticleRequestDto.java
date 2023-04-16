package kr.codesqaud.cafe.dto.article;

import kr.codesqaud.cafe.domain.Article;

import java.time.LocalDateTime;

public class ArticleRequestDto {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public ArticleRequestDto(final String writer, final String title, final String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = LocalDateTime.now();
    }

    public Article toEntity() {
        return new Article(writer, title, contents, writeTime);

    }
}
