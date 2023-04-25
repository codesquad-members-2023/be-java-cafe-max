package kr.codesqaud.cafe.app.question.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Question {

    private final Long id; // 게시글 등록번호
    private String title; // 제목
    private String content; // 내용
    private final LocalDateTime createTime; // 작성시간
    private final LocalDateTime modifyTime; // 갱신시간
    private final Long userId; // 회원 등록번호

    public Question(Long id, String title, String content, LocalDateTime createTime,
        LocalDateTime modifyTime, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
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
            ", userId=" + userId +
            '}';
    }
}
