package kr.codesqaud.cafe.repository.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Repository
public class ArticleJdbcRepository implements ArticleRepository {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

	private final JdbcTemplate jdbcTemplate;

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
		return jdbcTemplate.query("SELECT * FROM article",
			(rs, rowNum) -> new Article(
				rs.getLong("id"),
				rs.getString("writer"),
				rs.getString("title"),
				rs.getString("content"),
				LocalDateTime.parse(rs.getString("created_at"), FORMATTER))
		);
	}

	@Override
	public Optional<Article> findById(final Long id) {
		Article article = jdbcTemplate.queryForObject("SELECT * FROM article WHERE id = ?",
			(rs, rowNum) -> new Article(
				rs.getLong("id"),
				rs.getString("writer"),
				rs.getString("title"),
				rs.getString("content"),
				LocalDateTime.parse(rs.getString("created_at"), FORMATTER)),
			id
		);
		return Optional.ofNullable(article);
	}
}
