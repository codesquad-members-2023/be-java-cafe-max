package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
                Map.of("id", id),
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
                Map.of("id", id),
                Integer.class);
        return count > 0;
    }
}
