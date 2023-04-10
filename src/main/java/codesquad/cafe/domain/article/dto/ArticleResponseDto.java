package codesquad.cafe.domain.article.dto;

public class ArticleResponseDto {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String date;

    public ArticleResponseDto(final Long id, final String writer, final String title, final String contents, final String date) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public String getContents() {
        return contents;
    }
}
