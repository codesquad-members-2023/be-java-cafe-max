package kr.codesqaud.cafe.app.question.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import kr.codesqaud.cafe.app.user.entity.User;

public class Question {

    private final Long id; // 게시글 등록번호
    private String title; // 제목
    private String content; // 내용
    private final LocalDateTime createTime; // 작성시간
    private final LocalDateTime modifyTime; // 갱신시간
    private final User writer; // 회원 등록번호

    public Question(Long id, String title, String content, LocalDateTime createTime,
        LocalDateTime modifyTime, User writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.writer = writer;
    }

    public void modify(Question question) {
        this.title = question.getTitle();
        this.content = question.getContent();
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

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public User getWriter() {
        return writer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private String title;
        private String content;
        private LocalDateTime createTime;
        private LocalDateTime modifyTime;
        private User writer;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
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

        public Builder writer(User writer) {
            this.writer = writer;
            return this;
        }

        public Question build() {
            return new Question(id, title, content, createTime, modifyTime, writer);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", userId=" + writer +
            '}';
    }
}
