package codesquad.cafe.article.dtio;

import codesquad.cafe.article.domain.Article;

import java.time.LocalDateTime;

public class ArticleRequestDto {

    private String writer;
    private String title;
    private String contents;
    private LocalDateTime date;

    public ArticleRequestDto(final String writer, final String title, final String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.date = LocalDateTime.now();
    }

    public Article toEntity() {
        return new Article(writer, title, contents, date);
    }
}
