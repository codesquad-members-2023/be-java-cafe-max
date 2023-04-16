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
			.usingGeneratedKeyColumns("id");
	}

	@Override
	public Optional<Article> save(final Article article) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(article));
		return Optional.of(article);
	}

	@Override
	public List<Article> findAll() {
		return jdbcTemplate.query("SELECT * FROM article", articleMapper);
	}

	@Override
	public Optional<Article> findById(final Long id) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject("SELECT * FROM article WHERE id = :id", Map.of("id", id), articleMapper));
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
		jdbcTemplate.update("DELETE FROM article WHERE id=:id", Map.of("id", id));
	}
}
