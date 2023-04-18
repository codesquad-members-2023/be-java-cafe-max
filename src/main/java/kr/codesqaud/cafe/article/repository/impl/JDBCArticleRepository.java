package kr.codesqaud.cafe.article.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.domain.Reply;
import kr.codesqaud.cafe.article.repository.ArticleRepository;

@Repository
@Qualifier("jdbcRepository")
public class JDBCArticleRepository implements ArticleRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JDBCArticleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void save(Article article) {
		namedParameterJdbcTemplate.update(
			"INSERT INTO ARTICLE (title, content, date, id, nickName) VALUES (:title, :content, :date, :id, :nickName)",
			new MapSqlParameterSource()
				.addValue("title", article.getTitle())
				.addValue("content", article.getContent())
				.addValue("date", article.getDate())
				.addValue("id", article.getId())
				.addValue("nickName", article.getNickName())
		);

	}

	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(
			"SELECT title,content,date,id,nickName,idx FROM ARTICLE WHERE is_visible = true",
			(rs, rn) -> new Article(rs));
	}

	@Override
	public Optional<Article> findArticleByIdx(Long idx) {
		List<Article> article = namedParameterJdbcTemplate.query(
			"SELECT title,content,date,id,nickName,idx FROM ARTICLE WHERE idx = :idx AND is_visible = true",
			new MapSqlParameterSource("idx", idx),
			(rs, rn) -> new Article(rs));
		return article.stream().findFirst();
	}

	@Override
	public void updateArticle(Article article) {
		namedParameterJdbcTemplate.update("UPDATE ARTICLE SET title = :title, content = :content WHERE idx = :idx",
			new MapSqlParameterSource()
				.addValue("title", article.getTitle())
				.addValue("content", article.getContent())
				.addValue("idx", article.getIdx())
		);
	}

	@Override
	public void deleteArticle(Long idx) {
		namedParameterJdbcTemplate.update("UPDATE ARTICLE SET is_visible = false WHERE idx = :idx",
			new MapSqlParameterSource("idx", idx));
	}

	@Override
	public void saveReply(Reply reply) {
		namedParameterJdbcTemplate.update(
			"INSERT INTO REPLY (article_idx,id,nickName,content,date) VALUES (:article_idx, :id, :nickName, :content, :date)"
			, new MapSqlParameterSource()
				.addValue("article_idx", reply.getArticleIdx())
				.addValue("id", reply.getId())
				.addValue("nickName", reply.getNickName())
				.addValue("content", reply.getContent())
				.addValue("date", reply.getDate()));
	}

	@Override
	public List<Reply> findAllReply(Long replyIdx) {
		return namedParameterJdbcTemplate.query(
			"SELECT nickName,content,date,article_idx,idx FROM REPLY WHERE article_Idx = :idx AND is_visible = true",
			new MapSqlParameterSource("idx", replyIdx), (rs, rn) -> new Reply(rs));
	}

	@Override
	public void deleteReply(String id, Long replyIdx) {
		namedParameterJdbcTemplate.update("UPDATE REPLY SET is_visible = false WHERE idx = :replyIdx",
			new MapSqlParameterSource("replyIdx", replyIdx));
	}
}
