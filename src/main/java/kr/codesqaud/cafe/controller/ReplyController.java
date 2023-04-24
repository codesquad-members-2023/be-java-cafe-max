package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.SessionAttributeNames;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.dto.reply.ReplyResponse;
import kr.codesqaud.cafe.exception.user.AccessDeniedException;
import kr.codesqaud.cafe.repository.reply.MySqlReplyRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class ReplyController {

    private final MySqlReplyRepository replyRepository;

    public ReplyController(MySqlReplyRepository mySqlReplyRepository) {
        this.replyRepository = mySqlReplyRepository;
    }

    @PostMapping("/articles/{articleId}/replies")
    public ReplyResponse save(@PathVariable long articleId, HttpSession session, @RequestParam String contents) {
        final Reply reply = new Reply(articleId, session.getAttribute(SessionAttributeNames.LOGIN_USER_ID).toString(), contents);
        final long replyId = replyRepository.save(reply);
        return ReplyResponse.from(replyRepository.findByReplyId(replyId));
    }

    @DeleteMapping(value = "/articles/{articleId}/replies/{replyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable long articleId, @PathVariable long replyId, HttpSession session) {
        final Reply reply = replyRepository.findByReplyId(replyId);
        final String userId = session.getAttribute(SessionAttributeNames.LOGIN_USER_ID).toString();

        if (!reply.isReplyWriter(userId)) {
            throw new AccessDeniedException();
        }

        replyRepository.delete(replyId);
        return ResponseEntity.noContent().build();
    }
}
