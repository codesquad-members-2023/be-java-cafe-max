package kr.codesqaud.cafe.article;

/**
 * 게시글 엔티티
 */
public class Article {
    private final String writer;
    private final String title;
    private final String content;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
