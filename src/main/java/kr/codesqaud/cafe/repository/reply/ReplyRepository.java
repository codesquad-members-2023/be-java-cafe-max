package kr.codesqaud.cafe.repository.reply;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository {
    Reply save(Reply reply);

    Optional<Reply> findById(Long id);

    List<Reply> findByArticleId(Long articleId);
}
