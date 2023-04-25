package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    Reply save(Reply reply);

    Optional<Reply> findByReplyId(Long replyId);

    List<Reply> findAllByArticleId(Long articleId);

    void delete(Long replyId);

    void update(Reply reply);
}
