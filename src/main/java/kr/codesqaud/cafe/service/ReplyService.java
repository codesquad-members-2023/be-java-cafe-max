package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.ReplyForm;
import kr.codesqaud.cafe.exception.EmptyCommentException;
import kr.codesqaud.cafe.exception.ReplyNotFoundException;
import kr.codesqaud.cafe.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public boolean write(String userId, String writer, Long articleId, ReplyForm form) {
        Reply reply = new Reply.ReplyBuilder(userId, articleId)
                        .setContents(form.getContents())
                        .setWriter(writer)
                        .build();
        return replyRepository.save(reply);

    }

    public List<Reply> findReplies(Long articleId) {
        return replyRepository.findByArticleId(articleId)
                .stream()
                .filter(reply -> !reply.isDeleted())
                .collect(Collectors.toList());
    }

    public Reply findOne(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(ReplyNotFoundException::new);
        if (reply.isDeleted()) {
            throw new ReplyNotFoundException();
        }
        return reply;
    }

    public void update(Long id, String contents) {
        replyRepository.updateContents(id, contents);
    }

    public void delete(Long id) {
        replyRepository.delete(id);
    }
}
