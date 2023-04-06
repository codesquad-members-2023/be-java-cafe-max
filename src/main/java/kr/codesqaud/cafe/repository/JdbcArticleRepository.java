package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.codesqaud.cafe.domain.Article;

public class JdbcArticleRepository implements ArticleRepository {
	private final JdbcTemplate jdbcTemplate;

	public JdbcArticleRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Article save(Article article) {
		String sql = "INSERT INTO WRITE_INFO VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, article.getIndex(), article.getTitle(), article.getWriter(), article.getContents(),
			article.getWriteDate(), article.getHits());
		return article;
	}

	@Override
	public Optional<Article> findByIndex(Long index) {
		String sql = "SELECT * FROM WRITE_INFO WHERE index = ?";
		return jdbcTemplate.query(sql, articleRowMapper(), index).stream().findAny();
	}

	@Override
	public Optional<Article> findByTitle(String title) {
		String sql = "SELECT * FROM WRITE_INFO WHERE title = ?";
		return jdbcTemplate.query(sql, articleRowMapper(), title).stream().findAny();
	}

	@Override
	public Optional<Article> findByContents(String contents) {
		String sql = "SELECT * FROM WRITE_INFO WHERE contents = ?";
		return jdbcTemplate.query(sql, articleRowMapper(), contents).stream().findAny();
	}

	@Override
	public List<Article> findAll() {
		String sql = "SELECT * FROM WRITE_INFO";
		return jdbcTemplate.query(sql, articleRowMapper());
	}

	private RowMapper<Article> articleRowMapper() {
		return (rs, rowNum) -> new Article(rs.getLong("index"), rs.getString("title"), rs.getString("writer"),
			rs.getString("contents"), rs.getString("writeDate"), rs.getLong("hits"));
	}
}
