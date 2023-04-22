package kr.codesqaud.cafe.service.reply;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.dto.reply.ReplyForm;
import kr.codesqaud.cafe.domain.dto.reply.ReplyTimeForm;
import kr.codesqaud.cafe.repository.reply.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public ReplyTimeForm saveReply(Long articleId, ReplyForm form, String userId) {
        Reply reply = new Reply(articleId, userId, form.getReplyContent());
        Long replyId = replyRepository.save(reply);
        return ReplyTimeForm.from(replyId, reply);
    }

    public List<ReplyTimeForm> findReplies(Long articleId) {
        return replyRepository.findByArticleId(articleId).stream()
                .map(ReplyTimeForm::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public ReplyTimeForm findReplyId(Long id) {
        return ReplyTimeForm.from(findReply(id));
    }

    private Reply findReply(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("id 없음: " + id));
    }

    public void delete(Long id) {
        replyRepository.deleteReply(id);
    }
}
