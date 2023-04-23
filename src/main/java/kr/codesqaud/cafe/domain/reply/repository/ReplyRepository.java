package kr.codesqaud.cafe.domain.reply.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.reply.entity.Reply;

public interface ReplyRepository {
	void save(Reply reply);

	List<Reply> findByArticleId(Long articleId);

	void delete(String replyId);

	Optional<Reply> findById(String id);

}
