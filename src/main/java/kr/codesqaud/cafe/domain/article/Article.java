package kr.codesqaud.cafe.domain.article;

import java.time.LocalDateTime;

public class Article {

    private final Long id; // 게시글 등록번호
    private final String writer; // 작성자
    private final String title; // 제목
    private final String content; // 내용
    private final LocalDateTime writeDate; // 작성일자
    private final Long user_id; // 회원 등록번호

    public Article(Long id, String writer, String title, String content,
        LocalDateTime writeDate, Long user_id) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.user_id = user_id;
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
}
