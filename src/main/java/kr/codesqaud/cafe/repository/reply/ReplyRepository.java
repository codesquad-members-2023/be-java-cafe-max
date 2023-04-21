package kr.codesqaud.cafe.repository.reply;

import kr.codesqaud.cafe.domain.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    Reply save(Reply reply);
    Optional<Reply> findByReplyId(Long replyId);
    // 해당 게시글에 포함 된 모든 댓글 호출
    List<Reply> findByArticleId(Long articleId);
    Optional<Reply> update(Long replyId, String contents);
    Long delete(Long replyId);
}
