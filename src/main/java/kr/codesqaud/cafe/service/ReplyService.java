package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ReplyDto;
import kr.codesqaud.cafe.controller.dto.request.ReplyEditRequest;
import kr.codesqaud.cafe.controller.dto.request.ReplyRequest;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static kr.codesqaud.cafe.service.Utility.getUserIdFromSession;
import static kr.codesqaud.cafe.service.Utility.getUserNameFromSession;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void reply(Long articleId, ReplyRequest replyRequest, HttpServletRequest httpRequest) {
        Reply reply = Reply.from(articleId, getUserIdFromSession(httpRequest), getUserNameFromSession(httpRequest), replyRequest);
        replyRepository.save(reply);
    }

    public List<ReplyDto> getReplies(Long articleId){
        return replyRepository.findAllByArticleId(articleId)
                .stream()
                .map(ReplyDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public ReplyDto findByReplyId(final Long replyId) {
        return replyRepository.findByReplyId(replyId)
                .map(ReplyDto::from)
                .orElseThrow(() -> new RuntimeException("Reply not found"));
    }

    public void deleteReply(Long replyId) {
        replyRepository.delete(replyId);
    }

    public Reply editReply(final Long replyId, final ReplyEditRequest request) {
        Reply savedReply = replyRepository.findByReplyId(replyId).orElseThrow(() -> new RuntimeException("Reply not found"));
        savedReply.editReply(request.getNewComment());
        replyRepository.update(savedReply);
        return savedReply;
    }
}
