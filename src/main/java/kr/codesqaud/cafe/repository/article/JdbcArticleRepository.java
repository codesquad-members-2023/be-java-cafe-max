package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final Article article) {
        final String sql = "INSERT INTO articles (title, writer, contents, createdAt) VALUES (:title, :writer, :contents, :createdAt)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Article findById(final Long id) {
        final String sql = "SELECT id, title, writer, contents, createdAt FROM articles WHERE id = :id LIMIT 1";
        return jdbcTemplate.queryForStream(sql, Map.of("id", id), BeanPropertyRowMapper.newInstance(Article.class))
                .findFirst()
                .orElseThrow(ArticleNotFoundException::new);
    }

    @Override
    public List<Article> findAll() {
        final String sql = "SELECT id, title, writer, contents, createdAt FROM articles";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Article.class));
    }

    @Override
    public boolean exist(final Long id) {
        final String sql = "SELECT count(id) FROM articles WHERE id = :id LIMIT 1";
        final Integer count = jdbcTemplate.queryForObject(sql,
                Map.of("id", id),
                Integer.class);
        return count > 0;
    }

    @Override
    public Article findWithSurroundingArticles(Long id) {
        final String sql = "SELECT id, title, writer, contents, createdAt, previousId, nextId "
                + " FROM (SELECT id, title, writer, contents, createdAt, "
                + " LAG(id, 1, 0) OVER(ORDER BY id ASC) AS previousId, "
                + " LEAD(id, 1, 0) OVER(ORDER BY id ASC) AS nextId "
                + " FROM articles) WHERE id = :id";
        return jdbcTemplate.queryForStream(sql, Map.of("id", id), BeanPropertyRowMapper.newInstance(Article.class))
                .findFirst()
                .orElseThrow(ArticleNotFoundException::new);
    }
}
