package kr.codesqaud.cafe.domain.article.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.article.entity.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

@Repository
public class JdbcArticleRepositoryImpl implements ArticleRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcArticleRepositoryImpl(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public void save(Article article) {
		final String SQL = "INSERT INTO Article (title, content, dateTime) VALUES (:title, :content, :dateTime)";
		namedParameterJdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(article));
	}

	public Optional<Article> findById(Long id) {
		final String SQL = "SELECT * FROM Article WHERE id = :id";
		Article article = namedParameterJdbcTemplate.queryForObject(SQL,
			new MapSqlParameterSource().addValue("id", id),
			BeanPropertyRowMapper.newInstance(Article.class));
		if (article == null) {
			return Optional.empty();
		}
		return Optional.of(article);
	}

	public List<Article> findAll() {
		final String SQL = "SELECT * FROM Article";
		return namedParameterJdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Article.class));
	}
}
