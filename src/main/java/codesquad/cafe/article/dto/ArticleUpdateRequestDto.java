package codesquad.cafe.article.dto;

public class ArticleUpdateRequestDto {

    private String title;
    private String contents;

    public ArticleUpdateRequestDto(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
