package kr.codesqaud.cafe.DTO;

/**
 * 게시글 DTO 객체
 */
public class ArticleDTO {

    private String author;
    private String title;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
