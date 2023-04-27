package kr.codesqaud.cafe.controller.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleWithReplyCount {
    private final Long id;
    private final String writer;
    private final String title;
    private final String createdAt;
    private final Long replyCount;

    public ArticleWithReplyCount(Long id, String writer, String title, LocalDateTime createdAt, Long replyCount) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.createdAt = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm").format(createdAt);
        this.replyCount = replyCount;
    }
}
