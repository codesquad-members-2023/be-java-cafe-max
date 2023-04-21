package kr.codesqaud.cafe.repository.article;

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
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.exception.ArticleNotFoundException;

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
	public Article findByIndex(Long postIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("postIndex", postIndex);
		List<Article> articles = namedParameterJdbcTemplate.query(FIND_BY_INDEX, param, articleRowMapper());
		return OptionalTo(articles).orElseThrow(ArticleNotFoundException::new);
	}

	private Optional<Article> OptionalTo(List<Article> articles) {
		return articles.stream().findAny();
	}

	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(SELECT_ALL_FOR_WRITE_LIST, articleRowMapper());
	}

	@Override
	public boolean increaseHits(Long postIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("postIndex", postIndex);
		namedParameterJdbcTemplate.update(INCREASE_HITS, param);
		return true;
	}

	@Override
	public boolean delete(Long postIndex) {
		SqlParameterSource param = new MapSqlParameterSource("postIndex", postIndex);
		namedParameterJdbcTemplate.update(DELETE, param);
		return true;
	}

	@Override
	public boolean update(Long postIndex, ArticleDto articleDto) {
		SqlParameterSource params = new MapSqlParameterSource("postIndex", postIndex)
			.addValue("title", articleDto.getTitle())
			.addValue("writer", articleDto.getWriter())
			.addValue("contents", articleDto.getContents());
		namedParameterJdbcTemplate.update(UPDATE, params);
		return true;
	}

	private RowMapper<Article> articleRowMapper() {
		return (rs, rowNum) -> new Article(rs.getLong("postIndex"), rs.getString("title"), rs.getString("writer"),
			rs.getString("contents"), rs.getString("writeDate"), rs.getLong("hits"));
	}
}
