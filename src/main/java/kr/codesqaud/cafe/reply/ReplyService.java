package kr.codesqaud.cafe.reply;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public long save(long articleId, String userId, final ReplyRequestDto replyRequestDto) {
        Reply reply = new Reply.Builder()
                .articleId(articleId)
                .userId(userId)
                .contents(replyRequestDto.getContents())
                .build();
        long id = replyRepository.save(reply);
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
     * @param id reply id
     */
    public long deleteByReplyId(long id, String requesterId) {
        String originId = findOne(id).getLoginId();
        if (!requesterId.equals(originId)) {
            throw new IllegalArgumentException(); // TODO: 커스텀 에러로 변경
        }
        return replyRepository.deleteOneByReplyId(id);
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
}
