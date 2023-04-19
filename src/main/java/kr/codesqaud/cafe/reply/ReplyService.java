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
     * @param id : article id
     */
    public List<Reply> findReplies(long id) {
        return replyRepository.findAllByArticleId(id);
    }
}
