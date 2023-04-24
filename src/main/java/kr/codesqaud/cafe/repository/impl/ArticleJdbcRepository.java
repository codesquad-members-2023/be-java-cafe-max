package kr.codesqaud.cafe.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.controller.dto.ArticleWithCommentCount;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Repository
public class ArticleJdbcRepository implements ArticleRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final RowMapper<Article> articleMapper = (rs, rowNum) -> new Article(
		rs.getLong("id"),
		rs.getString("writer"),
		rs.getString("title"),
		rs.getString("content"),
		rs.getTimestamp("created_at").toLocalDateTime());

	public ArticleJdbcRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("article")
			.usingColumns("writer", "title", "content", "created_at")
			.usingGeneratedKeyColumns("id");
	}

	@Override
	public Optional<Article> save(final Article article) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(article));
		return Optional.of(article);
	}

	@Override
	public List<Article> findAll() {
		return jdbcTemplate.query("SELECT id, writer, title, content, created_at FROM article", articleMapper);
	}

	@Override
	public List<ArticleWithCommentCount> findAllArticleWithCommentCount() {
		return jdbcTemplate.query(
			"SELECT a.id, a.writer, a.title, a.content, a.created_at, COUNT(ac.id) AS comment_count "
				+ "FROM article AS a "
				+ "LEFT JOIN comment AS ac ON a.id = ac.article_id AND ac.is_deleted = FALSE "
				+ "WHERE a.is_deleted = FALSE "
				+ "GROUP BY a.id, a.writer, a.title, a.content, a.created_at",
			(rs, rowNum) -> new ArticleWithCommentCount(rs.getLong("id"),
			                                            rs.getString("writer"),
			                                            rs.getString("title"),
			                                            rs.getString("content"),
			                                            rs.getTimestamp("created_at").toLocalDateTime(),
			                                            rs.getLong("comment_count")));
	}

	@Override
	public Optional<Article> findById(final Long id) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject(
					"SELECT id, writer, title, content, created_at FROM article WHERE id = :id AND is_deleted = FALSE",
					Map.of("id", id), articleMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void update(final Article article) {
		Map<String, Object> params = Map.of("title", article.getTitle(),
		                                    "content", article.getContent(),
		                                    "id", article.getId());
		jdbcTemplate.update("UPDATE article SET title=:title, content=:content WHERE id=:id", params);
	}

	@Override
	public void deleteById(final Long id) {
		jdbcTemplate.update("UPDATE article AS a "
			                    + "LEFT JOIN comment AS ac ON a.id = ac.article_id "
			                    + "SET a.is_deleted = TRUE, ac.is_deleted = TRUE "
			                    + "WHERE a.id = :id",
		                    Map.of("id", id));
	}

	@Override
	public boolean isPossibleDeleteById(final Long id) {
		try {
			return Boolean.FALSE.equals(
				jdbcTemplate.queryForObject("SELECT EXISTS ("    // 댓글 작성자 중 게시글 작성자와 일치하지 않은 사용자가 존재할 경우 TRUE 반환
					                            + "SELECT a.id FROM article AS a "
					                            + "LEFT JOIN comment AS ac ON a.id = ac.article_id AND ac.is_deleted = FALSE "
					                            + "WHERE a.id = :id AND a.writer NOT LIKE ac.writer)",
				                            Map.of("id", id), Boolean.class));
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
}
