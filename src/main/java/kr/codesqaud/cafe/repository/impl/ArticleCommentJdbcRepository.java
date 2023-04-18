package kr.codesqaud.cafe.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.articlecomment.ArticleComment;
import kr.codesqaud.cafe.repository.ArticleCommentRepository;

@Repository
public class ArticleCommentJdbcRepository implements ArticleCommentRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public ArticleCommentJdbcRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("article_comment")
			.usingColumns("content", "created_at", "writer", "article_id")
			.usingGeneratedKeyColumns("id");
	}

	@Override
	public Optional<ArticleComment> save(final ArticleComment articleComment) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(articleComment));
		return Optional.of(articleComment);
	}

	public List<ArticleComment> findAllByArticleId(final Long articleId) {
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
