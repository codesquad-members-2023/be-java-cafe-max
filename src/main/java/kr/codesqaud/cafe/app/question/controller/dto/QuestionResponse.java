package kr.codesqaud.cafe.app.question.controller.dto;

import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.util.LocalDateTimeUtil;

public class QuestionResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String createTime;
    private final Long userId;
    private final String writer;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.createTime = LocalDateTimeUtil.formatLocalDateTime(question.getCreateTime());
        this.userId = question.getWriter().getId();
        this.writer = question.getWriter().getName();
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

    public String getCreateTime() {
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
            "ArticleResponseDto{id=%d, writer=%s, title=%s, content=%s, createTime=%s, user_id=%d}",
            id, writer, title, content, createTime, userId);
    }
}
