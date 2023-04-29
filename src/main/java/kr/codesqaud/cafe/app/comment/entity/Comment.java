package kr.codesqaud.cafe.app.comment.entity;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.user.entity.User;

public class Comment {

    private final Long id;
    private String content;
    private final LocalDateTime createTime;
    private final LocalDateTime modifyTime;
    private final Boolean deleted;
    private final Question question;
    private final User writer;

    private Comment(Long id, String content, LocalDateTime createTime, LocalDateTime modifyTime,
        Boolean deleted, Question question, User writer) {
        this.id = id;
        this.content = content;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.deleted = deleted;
        this.question = question;
        this.writer = writer;
    }

    public void modify(Comment comment) {
        this.content = comment.getContent();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        private Long id;
        private String content;
        private LocalDateTime createTime;
        private LocalDateTime modifyTime;
        private Boolean deleted;
        private Question question;
        private User writer;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder modifyTime(LocalDateTime modifyTime) {
            this.modifyTime = modifyTime;
            return this;
        }

        public Builder deleted(Boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder question(Question question) {
            this.question = question;
            return this;
        }

        public Builder writer(User writer) {
            this.writer = writer;
            return this;
        }

        public Comment build() {
            return new Comment(id, content, createTime, modifyTime, deleted, question, writer);
        }
    }
}
