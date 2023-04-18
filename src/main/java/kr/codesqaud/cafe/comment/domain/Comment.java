package kr.codesqaud.cafe.comment.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    private final long articleId;
    private final String contents;
    private final String userId;
    private final LocalDateTime createdTime;

    public Comment(long articleId, String contents, String userId, LocalDateTime createdTime) {
        this.articleId = articleId;
        this.contents = contents;
        this.userId = userId;
        this.createdTime = Objects.requireNonNullElseGet(createdTime, LocalDateTime::now);
    }

    public long getArticleId() {
        return articleId;
    }

    public String getContents() {
        return contents;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
