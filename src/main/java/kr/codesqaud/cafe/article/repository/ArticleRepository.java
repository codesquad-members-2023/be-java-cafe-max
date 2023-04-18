package kr.codesqaud.cafe.article.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.domain.Reply;

public interface ArticleRepository {

	void save(Article article);

	List<Article> findAll();

	Optional<Article> findArticleByIdx(Long idx);

	void updateArticle(Article article);

	void deleteArticle(Long idx);

	void saveReply(Reply reply);

	List<Reply> findAllReply(Long idx);

	void deleteReply(String id, Long replyIdx);

	String findReplyIdByIdx(Long replyIdx);
}
