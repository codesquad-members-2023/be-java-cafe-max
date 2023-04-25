package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;

import java.util.List;

public interface ReplyRepository {
    List<Reply> findAll(Long articleId);

    Long save(Reply reply);

    boolean update(Reply reply);
}
