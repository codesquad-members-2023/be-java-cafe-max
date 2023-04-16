package kr.codesqaud.cafe.article;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

public class H2ArticleRepository implements ArticleRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        final String SQL = "INSERT INTO Articles (writer, title, contents, createdAt) VALUES (:writer, :title, :contents, :createdAt)";
        jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(article));
    }

    @Override
    public List<ArticleDTO> findAll() {
        final String SQL = "SELECT * FROM Articles";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(ArticleDTO.class));
    }

    @Override
    public ArticleDTO findById(BigInteger articleId) {
        SqlParameterSource namedParameterSource = new MapSqlParameterSource().addValue("articleId", articleId);
        final String SQL = "SELECT * FROM Articles WHERE articleId = :articleId";
        return jdbcTemplate.queryForObject(SQL, namedParameterSource, new BeanPropertyRowMapper<>(ArticleDTO.class));
    }
}
