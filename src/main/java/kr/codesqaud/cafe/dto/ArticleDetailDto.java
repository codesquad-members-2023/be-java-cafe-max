package kr.codesqaud.cafe.dto;


public class ArticleDetailDto {
    //세부적인 글 내용 DTO
    private final String author;

    private final String title;

    private final String contents;

    public ArticleDetailDto(String author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }


}
