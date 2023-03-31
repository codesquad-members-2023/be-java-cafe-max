package kr.codesqaud.cafe.domain;

public class Post {

    private final long id;
    private final String writer;
    private final String title;
    private final String contents;

    public Post(long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }
}
