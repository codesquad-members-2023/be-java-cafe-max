package kr.codesqaud.cafe.repository.article;

import static kr.codesqaud.cafe.dto.Paging.*;
import static kr.codesqaud.cafe.repository.article.ArticleSql.*;

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
	public Optional<Article> findByArticleIndex(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex);
		List<Article> articles = namedParameterJdbcTemplate.query(FIND_BY_INDEX, param, articleRowMapper());
		return optionalTo(articles);
	}

	private Optional<Article> optionalTo(List<Article> articles) {
		return articles.stream().findAny();
	}

	@Override
	public List<Article> findPage(int page) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("page", PAGE_SIZE * (page - 1))
			.addValue("pageSize", PAGE_SIZE);
		return namedParameterJdbcTemplate.query(SELECT_ALL_FOR_ARTICLE_LIST, param, articleRowMapper());
	}

	@Override
	public boolean increaseHits(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex);
		namedParameterJdbcTemplate.update(INCREASE_HITS, param);
		return true;
	}

	@Override
	public boolean delete(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource("articleIndex", articleIndex);
		namedParameterJdbcTemplate.update(DELETE, param);
		return true;
	}

	@Override
	public boolean update(Long articleIndex, Article article) {
		SqlParameterSource params = new MapSqlParameterSource("articleIndex", articleIndex)
			.addValue("title", article.getTitle())
			.addValue("contents", article.getContents())
			.addValue("modDate", article.getModDate());
		namedParameterJdbcTemplate.update(UPDATE, params);
		return true;
	}

	@Override
	public Article findWriterByArticleIndex(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex);
		return namedParameterJdbcTemplate.query(SELECT_WRITER_BY_ARTICLE_INDEX, param,
			articleWriterRowMapper()).get(0);
	}

	@Override
	public Integer getArticleSize() {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("deleted", false);
		return namedParameterJdbcTemplate.queryForObject(COUNT_ARTICLE_SIZE, param, Integer.class);
	}

	private RowMapper<Article> articleRowMapper() {
		return (rs, rowNum) -> new Article(rs.getLong("articleIndex"), rs.getString("title"), rs.getString("writer"),
			rs.getString("contents"), rs.getString("writeDate"), rs.getLong("hits"));
	}

	private RowMapper<Article> articleWriterRowMapper() {
		return (rs, rowNum) -> new Article(rs.getString("writer"));
	}
}
