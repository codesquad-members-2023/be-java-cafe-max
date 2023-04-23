package kr.codesqaud.cafe.app.question.controller.dto;

import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.app.question.entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionSavedRequest {

    private static final Logger logger = LoggerFactory.getLogger(QuestionSavedRequest.class);

    @Pattern(regexp = "^.{1,100}$", message = "제목은 100자 이내여야 합니다.")
    private final String title;
    private final String content;
    @Pattern(regexp = "^[a-z\\d_-]{5,20}$", message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    private final String userId;

    public QuestionSavedRequest() {
        this(null, null, null);
    }

    public QuestionSavedRequest(String title, String content, String userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Question toEntity(Long userId) {
        return new Question(null, title, content, null, null, userId);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format(
            "ArticleSavedRequestDto{title=%s, content=%s, userId=%s}",
            title, content, userId);
    }
}
