package kr.codesqaud.cafe.app.question.controller.dto;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.user.entity.User;

public class QuestionResponse implements Comparable<QuestionResponse> {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createTime;
    private final Long userId;
    private final String writer;

    public QuestionResponse(Question question, User user) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.createTime = question.getCreateTime();
        this.userId = user.getId();
        this.writer = user.getName();
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public String getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return String.format(
            "ArticleResponseDto{id=%d, writer=%s, title=%s, content=%s, writeDate=%s, user_id=%d}",
            id, writer, title, content, createTime, userId);
    }

    @Override
    public int compareTo(QuestionResponse dto) {
        return dto.getCreateTime().compareTo(this.createTime);
    }
}
