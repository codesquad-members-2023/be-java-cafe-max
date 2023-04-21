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
		return jdbcTemplate.query("SELECT * FROM article", (rs, rowNum) -> new Article(
			rs.getString("writer"),
			rs.getString("title"),
			rs.getString("contents"),
			rs.getLong("id"),
			rs.getTimestamp("created_at").toLocalDateTime()
		));
	}

	@Override
	public Article findPosting(Long id) {
		return jdbcTemplate.queryForObject("SELECT * FROM article WHERE id = ?", (rs, rowNum) -> new Article(
			rs.getString("writer"),
			rs.getString("title"),
			rs.getString("contents"),
			rs.getLong("id"),
			rs.getTimestamp("created_at").toLocalDateTime()
		), id);
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("DELETE FROM article WHERE id = ?", id);
	}

	@Override
	public void update(Article article, Long id) {
		jdbcTemplate.update("UPDATE article SET title = ? ,contents = ? WHERE id = ?",
			article.getTitle(),
			article.getContents(),
			id);
	}
}
