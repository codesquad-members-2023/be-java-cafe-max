package kr.codesqaud.cafe.web.dto.article;

import java.time.LocalDateTime;
import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.domain.article.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleSavedRequestDto {

    private static final Logger logger = LoggerFactory.getLogger(ArticleSavedRequestDto.class);

    @Pattern(regexp = "^[a-z가-힣]{1,20}$", message = "1~20자 영문 소문자, 한글만 사용 가능합니다.")
    private final String writer;
    @Pattern(regexp = "^.{1,100}$", message = "제목은 100자 이내여야 합니다.")
    private final String title;
    private final String content;
    private final LocalDateTime writeDate;
    @Pattern(regexp = "^[a-z\\d_-]{5,20}$", message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    private final String userId;

    public ArticleSavedRequestDto() {
        this(null, null, null, LocalDateTime.now(), null);
    }

    public ArticleSavedRequestDto(String writer, String title, String content,
        LocalDateTime writeDate, String userId) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.userId = userId;
        logger.info("매개변수 생성자 : " + this);
    }

    public Article toEntity(Long nextId, Long user_id) {
        return new Article(nextId, writer, title, content, writeDate, user_id);
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

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format(
            "ArticleSavedRequestDto{writer=%s, title=%s, content=%s, writeDate=%s, userId=%s}",
            writer, title, content, writeDate, userId);
    }
}
