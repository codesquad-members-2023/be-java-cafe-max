package kr.codesqaud.cafe.web.dto.article;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.domain.article.Article;

public class ArticleResponseDto implements Comparable<ArticleResponseDto> {

    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime writeDate;
    private final Long user_id;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.writeDate = article.getWriteDate();
        this.user_id = article.getUser_id();
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return String.format(
            "ArticleResponseDto{id=%d, writer=%s, title=%s, content=%s, writeDate=%s, user_id=%d}",
            id, writer, title, content, writeDate, user_id);
    }

    @Override
    public int compareTo(ArticleResponseDto dto) {
        return dto.getWriteDate().compareTo(this.writeDate);
    }
}
