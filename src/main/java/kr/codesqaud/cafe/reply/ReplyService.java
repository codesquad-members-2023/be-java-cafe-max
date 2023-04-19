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

    /**
     * @param id reply id
     */
    public long deleteByReplyId(long id, String requesterId) {
        String originId = findOne(id).getUserId();
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
