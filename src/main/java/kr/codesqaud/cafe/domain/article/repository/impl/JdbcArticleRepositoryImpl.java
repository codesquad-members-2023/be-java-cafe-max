package kr.codesqaud.cafe.domain.article.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.article.entity.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

@Repository
public class JdbcArticleRepositoryImpl implements ArticleRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final RowMapper<Article> articleRowMapper = BeanPropertyRowMapper.newInstance(Article.class);

	public JdbcArticleRepositoryImpl(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public void save(Article article) {
		final String SQL = "INSERT INTO Article (title, content, dateTime, writer) VALUES (:title, :content, :dateTime,:writer)";
		namedParameterJdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(article));
	}

	public Optional<Article> findById(Long id) {
		final String SQL = "SELECT * FROM Article WHERE id = :id";
		try {
			return namedParameterJdbcTemplate.queryForStream(SQL, Map.of("id", id), articleRowMapper).findFirst();
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Article> findAll() {
		final String SQL = "SELECT * FROM Article";
		return namedParameterJdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Article.class));
	}
}
