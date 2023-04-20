package codesquad.cafe.domain.reply.service;

import codesquad.cafe.domain.reply.domain.Reply;
import codesquad.cafe.domain.reply.dto.ReplyRequestDto;
import codesquad.cafe.domain.reply.repository.ReplyRepository;
import codesquad.cafe.domain.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(final ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void createReply(final Long postId, final User user, final ReplyRequestDto replyRequestDto) {
        replyRepository.save(new Reply(postId, user, replyRequestDto));
    }
}
