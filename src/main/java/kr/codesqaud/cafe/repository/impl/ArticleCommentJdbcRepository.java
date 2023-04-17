package kr.codesqaud.cafe.repository.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.articlecomment.ArticleComment;
import kr.codesqaud.cafe.repository.ArticleCommentRepository;

@Repository
public class ArticleCommentJdbcRepository implements ArticleCommentRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ArticleCommentJdbcRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<ArticleComment> findAllByArticleId(Long articleId) {
		return jdbcTemplate.query(
			"SELECT id, content, created_at, writer, article_id FROM article_comment WHERE is_deleted = FALSE AND article_id = :articleId",
			Map.of("articleId", articleId),
			(rs, rowNum) -> new ArticleComment(
				rs.getLong("id"),
				rs.getString("content"),
				rs.getTimestamp("created_at").toLocalDateTime(),
				rs.getString("writer"),
				rs.getLong("article_id")
			));
	}
}
