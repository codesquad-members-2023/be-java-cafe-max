package kr.codesqaud.cafe.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Repository
public class ArticleJdbcRepository implements ArticleRepository {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Article> articleMapper = (rs, rowNum) -> new Article(
		rs.getLong("id"),
		rs.getString("writer"),
		rs.getString("title"),
		rs.getString("content"),
		rs.getTimestamp("created_at").toLocalDateTime());

	public ArticleJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Article> save(final Article article) {
		jdbcTemplate.update("INSERT INTO article(writer, title, content, created_at) VALUES (?, ?, ?, ?)",
			article.getWriter(),
			article.getTitle(),
			article.getContent(),
			article.getCreatedAt()
		);

		return Optional.of(article);
	}

	@Override
	public List<Article> findAll() {
		return jdbcTemplate.query("SELECT * FROM article", articleMapper);
	}

	@Override
	public Optional<Article> findById(final Long id) {
		Article article = jdbcTemplate.queryForObject("SELECT * FROM article WHERE id = ?",
			articleMapper,
			id
		);
		return Optional.ofNullable(article);
	}
}
