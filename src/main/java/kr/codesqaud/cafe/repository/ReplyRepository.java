package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    boolean save(Reply reply);

    List<Reply> findByArticleId(Long articleId);

    Optional<Reply> findById(Long id);

    void updateContents(Long id, String updateContents);

    void delete(Long id);
}
