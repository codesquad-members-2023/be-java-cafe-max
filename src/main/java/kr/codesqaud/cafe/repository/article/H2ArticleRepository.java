package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class H2ArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        final String SQL = "INSERT INTO articles (title, writer, contents, createdAt) VALUES (:title, :writer, :contents, :createdAt)";
        jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(article));
    }

    @Override
    public Article findById(Long id) {
        final String SQL = "SELECT * FROM articles WHERE id = :id";
        return jdbcTemplate.queryForObject(SQL,
                new MapSqlParameterSource().addValue("id", id),
                BeanPropertyRowMapper.newInstance(Article.class));
    }

    @Override
    public List<Article> findAll() {
        final String SQL = "SELECT * FROM articles";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Article.class));
    }

    @Override
    public boolean isExists(Long id) {
        final String SQL = "SELECT count(*) FROM articles WHERE id = :id";
        final Integer count = jdbcTemplate.queryForObject(SQL,
                new MapSqlParameterSource().addValue("id", id),
                Integer.class);
        return count > 0;
    }
}
