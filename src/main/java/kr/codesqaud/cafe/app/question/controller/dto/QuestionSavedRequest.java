package kr.codesqaud.cafe.app.question.controller.dto;

import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionSavedRequest {

    private static final Logger log = LoggerFactory.getLogger(QuestionSavedRequest.class);

    @Pattern(regexp = "^.{1,100}$", message = "제목은 100자 이내여야 합니다.")
    private String title;
    private String content;
    private Long userId;

    public QuestionSavedRequest() {
    }

    public QuestionSavedRequest(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Question toEntity() {
        return Question.builder()
            .title(title)
            .content(content)
            .writer(User.builder().id(userId).build())
            .build();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "QuestionSavedRequest{" +
            "title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", writer='" + userId + '\'' +
            '}';
    }
}
