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
	public boolean deleteArticle(Long idx, String id) {
		int rowsAffected = namedParameterJdbcTemplate.update(
			"UPDATE ARTICLE A SET A.is_visible = FALSE WHERE A.idx = :idx "
				+ "AND NOT EXISTS (SELECT 1 FROM REPLY B WHERE B.article_idx = A.idx AND B.is_visible = TRUE AND B.id != :id)",
			new MapSqlParameterSource()
				.addValue("idx", idx)
				.addValue("id", id)
		);
		return (rowsAffected > 0);
	}
}
