package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.controller.dto.Pageable;
import kr.codesqaud.cafe.domain.Reply;

import java.util.List;

public interface ReplyRepository {
    List<Reply> findReplies(Long articleId, Pageable pageable);

    Long save(Reply reply);

    boolean update(Reply reply);

    boolean delete(Long replyId, Long userId);
}
