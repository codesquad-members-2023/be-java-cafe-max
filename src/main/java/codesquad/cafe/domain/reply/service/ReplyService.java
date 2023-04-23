package codesquad.cafe.domain.reply.service;

import codesquad.cafe.domain.reply.domain.Reply;
import codesquad.cafe.domain.reply.dto.ReplyRequestDto;
import codesquad.cafe.domain.reply.dto.ReplyResponseDto;
import codesquad.cafe.domain.reply.repository.ReplyRepository;
import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.global.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static codesquad.cafe.global.exception.ErrorCode.NOT_MATCH_USER_AND_REPLY;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(final ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void createReply(final Long postId, final User user, final ReplyRequestDto replyRequestDto) {
        replyRepository.save(new Reply(postId, user, replyRequestDto));
    }

    public List<ReplyResponseDto> findReplies(final Long postId) {
        List<Reply> replies = replyRepository.findAllByPost(postId);
        List<ReplyResponseDto> replyResponseDtos = new ArrayList<>();
        for (Reply reply : replies) {
            String userName = replyRepository.findUserNameByReply(reply);
            replyResponseDtos.add(reply.toDto(userName));
        }
        return replyResponseDtos;
    }

    public void deleteReply(final User user, final Long replyId) {
        User replyUser = replyRepository.findUserById(replyId);
        validateReplyUser(user, replyUser);
        replyRepository.delete(replyId);
    }

    private void validateReplyUser(final User user, final User replyUser) {
        if (!user.getId().equals(replyUser.getId())) {
            throw new CustomException(NOT_MATCH_USER_AND_REPLY);
        }
    }
}
