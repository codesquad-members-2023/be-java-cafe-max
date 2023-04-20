package kr.codesqaud.cafe.article.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.article.domain.Article;
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
			"INSERT INTO ARTICLE (title, content, date, user_id) VALUES (:title, :content, :date, :userId)",
			new MapSqlParameterSource()
				.addValue("title", article.getTitle())
				.addValue("content", article.getContent())
				.addValue("date", article.getDate())
				.addValue("userId", article.getUserId())
		);

	}

	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(
			"SELECT A.nickName, B.* FROM USER A INNER JOIN ARTICLE B ON A.user_id = B.user_id;",
			(rs, rn) -> new Article(rs));
	}

	@Override
	public Optional<Article> findArticleByIdx(Long articleIdx) {
		List<Article> article = namedParameterJdbcTemplate.query(
			"SELECT A.nickName, B.* FROM USER A JOIN ARTICLE B ON A.user_id = B.user_id WHERE B.article_idx = :articleIdx AND B.is_visible = true",
			new MapSqlParameterSource("articleIdx", articleIdx),
			(rs, rn) -> new Article(rs));
		return article.stream().findFirst();
	}

	@Override
	public void updateArticle(Article article) {
		namedParameterJdbcTemplate.update(
			"UPDATE ARTICLE SET title = :title, content = :content WHERE article_idx = :articleIdx",
			new MapSqlParameterSource()
				.addValue("title", article.getTitle())
				.addValue("content", article.getContent())
				.addValue("articleIdx", article.getArticleIdx())
		);
	}

	@Override
	public boolean deleteArticle(Long articleIdx, String userId) {
		int rowsAffected = namedParameterJdbcTemplate.update(
			"UPDATE ARTICLE A SET A.is_visible = FALSE WHERE A.article_idx = :articleIdx "
				+ "AND NOT EXISTS (SELECT 1 FROM REPLY B WHERE B.article_idx = A.article_idx AND B.is_visible = TRUE AND B.user_id != :userId)",
			new MapSqlParameterSource()
				.addValue("articleIdx", articleIdx)
				.addValue("userId", userId)
		);
		return (rowsAffected > 0);
	}
}
