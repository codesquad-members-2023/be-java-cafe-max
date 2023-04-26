package kr.codesqaud.cafe.app.comment.controller.dto;

import javax.validation.constraints.Size;
import kr.codesqaud.cafe.app.comment.entity.Comment;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.user.entity.User;

public class CommentSavedRequest {

    @Size(min = 1, max = 3000, message = "3000자 이내로 입력해주세요.")
    private String content;
    private Long questionId;
    private Long userId;

    public CommentSavedRequest() {

    }

    public CommentSavedRequest(String content, Long questionId, Long userId) {
        this.content = content;
        this.questionId = questionId;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public Comment toEntity() {
        return Comment.builder()
            .content(content)
            .question(Question.builder().id(questionId).build())
            .writer(User.builder().id(userId).build())
            .build();
    }
}
