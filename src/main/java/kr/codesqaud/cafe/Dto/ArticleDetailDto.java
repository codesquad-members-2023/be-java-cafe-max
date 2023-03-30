package kr.codesqaud.cafe.Dto;

import java.time.LocalDateTime;

public class ArticleDetailDto {
    String author;

    String title;

    String contents;

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
