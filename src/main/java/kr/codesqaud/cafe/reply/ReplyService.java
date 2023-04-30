package kr.codesqaud.cafe.reply;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @Transactional
    public long save(long articleId, String userId, final ReplyRequestDto replyRequestDto) {
        Reply reply = new Reply.Builder()
                .articleId(articleId)
                .userId(userId)
                .contents(replyRequestDto.getContents())
                .build();
        long id = replyRepository.save(reply);

        replyRepository.updateHasReply(articleId, true);
        logger.info("댓글 저장 성공, 댓글 id: {}", id);
        return id;
    }

    // TODO: 댓글 수정 기능 구현 필요
//    public long edit(long id, String requesterId) {
//        Reply findReply = replyRepository.findById(id);
//        String originId = findReply.getUserId();
//        if (!originId.equals(requesterId)) {
//            logger.info("댓글 수정 요청 Id와 댓글 Id 불일치, 댓글 Id: {}", findReply.getId());
//            throw new IllegalArgumentException(); // TODO: 커스텀 에러로 변경
//        }
//        logger.info("댓글 수정 성공, 댓글 Id: {}", findReply.getId());
//        return replyRepository.update(findReply);
//    }

    /**
     * @return 댓글 삭제 후 댓글 개수
     */
    @Transactional
    public long deleteByReplyId(long articleId, long replyId, String requesterId) {
        String originId = findOne(replyId).getLoginId();
        if (!requesterId.equals(originId)) {
            throw new IllegalArgumentException(); // TODO: 커스텀 에러로 변경
        }
        if (replyRepository.getReplyCountOf(articleId) == 1) {
            replyRepository.updateHasReply(articleId, false);
        }

        replyRepository.deleteOneByReplyId(replyId);

        logger.info("댓글 삭제 성공, 게시글 id : {}, 댓글 id : {}", articleId, replyId);
        return replyRepository.getReplyCountOf(articleId);
    }

    public Reply findOne(long id) {
        return replyRepository.findById(id);
    }

    /**
     * @param id : article id
     */
    public List<Reply> findReplies(long id) {
        return replyRepository.findAllByArticleId(id);
    }

    public Long getReplyCountOf(long articleId) {
        return replyRepository.getReplyCountOf(articleId);
    }
}
