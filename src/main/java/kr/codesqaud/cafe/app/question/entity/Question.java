package kr.codesqaud.cafe.app.question.entity;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.app.user.entity.User;

public class Question {

    private final Long id; // 게시글 등록번호
    private final String title; // 제목
    private final String content; // 내용
    private final LocalDateTime writeDate; // 작성일자
    private final User user; // 회원

    public Question(Long id, String title, String content,
        LocalDateTime writeDate, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.user = user;
    }

    public Long getId() {
        return id;
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

    public User getUser() {
        return user;
    }
}
