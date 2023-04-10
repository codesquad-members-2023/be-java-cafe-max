package codesquad.cafe.domain.article.dto;

public class ArticleResponseDto {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String createdAt;

    public ArticleResponseDto(final Long id, final String writer, final String title, final String contents, final String createdAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
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
}
