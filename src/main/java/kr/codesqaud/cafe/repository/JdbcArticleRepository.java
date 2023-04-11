package kr.codesqaud.cafe.repository;

import static kr.codesqaud.cafe.repository.ArticleSql.*;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Article;

@Repository
public class JdbcArticleRepository implements ArticleRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcArticleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Article create(Article article) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(article);
		namedParameterJdbcTemplate.update(CREATE, params);
		return article;
	}

	@Override
	public Optional<Article> findByIndex(Long index) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("index", index);
		List<Article> articles = namedParameterJdbcTemplate.query(FIND_BY_INDEX, param, articleRowMapper());
		return OptionalTo(articles);
	}

	private Optional<Article> OptionalTo(List<Article> articles) {
		return articles.stream().findAny();
	}

	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(SELECT_ALL_FOR_WRITE_LIST, articleRowMapper());
	}

	@Override
	public boolean increaseHits(Long index) {
		long newHits = findByIndex(index).get().getHits();
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("index", index)
			.addValue("hits", ++newHits);
		namedParameterJdbcTemplate.update(INCREASE_HITS, param);
		return true;
	}

	private RowMapper<Article> articleRowMapper() {
		return (rs, rowNum) -> new Article(rs.getLong("index"), rs.getString("title"), rs.getString("writer"),
			rs.getString("contents"), rs.getString("writeDate"), rs.getLong("hits"));
	}
}
