package kr.codesqaud.cafe.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Article;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcArticleRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Article article) {
		jdbcTemplate.update("INSERT INTO article(writer, title, contents, created_at) values (?, ?, ?, ?)",
			article.getWriter(),
			article.getTitle(),
			article.getContents(),
			article.getCreateAt());
	}

	@Override
	public List<Article> findAllPosting() {
		return jdbcTemplate.query("SELECT * FROM ARTICLE", (rs, rowNum) -> new Article(
			rs.getString("writer"),
			rs.getString("title"),
			rs.getString("contents"),
			rs.getLong("id"),
			rs.getTimestamp("created_at").toLocalDateTime()
		));
	}

	@Override
	public Article findPosting(Long id) {
		return jdbcTemplate.queryForObject("SELECT * FROM ARTICLE WHERE id = ?", (rs, rowNum) -> new Article(
			rs.getString("writer"),
			rs.getString("title"),
			rs.getString("contents"),
			rs.getLong("id"),
			rs.getTimestamp("created_at").toLocalDateTime()
		), id);
	}
}
