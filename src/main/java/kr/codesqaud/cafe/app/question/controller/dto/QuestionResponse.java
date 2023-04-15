package kr.codesqaud.cafe.app.question.controller.dto;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.app.question.entity.Question;

public class QuestionResponse implements Comparable<QuestionResponse> {

    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime writeDate;
    private final Long userId;

    public QuestionResponse() {
        this.id = null;
        this.writer = null;
        this.title = null;
        this.content = null;
        this.writeDate = null;
        this.userId = null;
    }

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.writer = question.getUser().getName();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.writeDate = question.getWriteDate();
        this.userId = question.getUser().getId();
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

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format(
            "ArticleResponseDto{id=%d, writer=%s, title=%s, content=%s, writeDate=%s, user_id=%d}",
            id, writer, title, content, writeDate, userId);
    }

    @Override
    public int compareTo(QuestionResponse dto) {
        return dto.getWriteDate().compareTo(this.writeDate);
    }
}
