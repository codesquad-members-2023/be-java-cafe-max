package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.common.exception.reply.ReplyDeleteFailException;
import kr.codesqaud.cafe.common.exception.reply.ReplyUpdateFailException;
import kr.codesqaud.cafe.controller.dto.Pageable;
import kr.codesqaud.cafe.controller.dto.reply.ReplyCreateDto;
import kr.codesqaud.cafe.controller.dto.reply.ReplyListDto;
import kr.codesqaud.cafe.controller.dto.reply.ReplyUpdateDto;
import kr.codesqaud.cafe.repository.ReplyRepository;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public ReplyListDto findReplies(Long articleId, Pageable pageable) {
        return ReplyListDto.of(replyRepository.findReplies(articleId, pageable), pageable);
    }

    public Long save(ReplyCreateDto replyCreateDto) {
        return replyRepository.save(replyCreateDto.toReply());
    }

    public void update(Long userId, ReplyUpdateDto replyUpdateDto) {
        final boolean isUpdated = replyRepository.update(replyUpdateDto.toReply(userId));

        if (isUpdated) {
            return;
        }

        throw new ReplyUpdateFailException();
    }

    public void delete(Long replyId, Long userId) {
        final boolean isDeleted = replyRepository.delete(replyId, userId);

        if (isDeleted) {
            return;
        }

        throw new ReplyDeleteFailException();
    }
}
