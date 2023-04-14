package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
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
    private final RowMapper<Article> articleRowMapper = BeanPropertyRowMapper.newInstance(Article.class);

    public JdbcArticleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Article article) {
        final String sql = "INSERT INTO articles (title, writer, contents, createdAt) VALUES (:title, :writer, :contents, :createdAt)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Article findById(Long id) {
        final String sql = "SELECT id, title, writer, contents, createdAt FROM articles WHERE id = :id AND deleted = false LIMIT 1";
        return jdbcTemplate.queryForStream(sql, Map.of("id", id), articleRowMapper)
                .findFirst()
                .orElseThrow(ArticleNotFoundException::new);
    }

    @Override
    public List<Article> findAll() {
        final String sql = "SELECT id, title, writer, contents, createdAt FROM articles WHERE deleted = false";
        return jdbcTemplate.query(sql, articleRowMapper);
    }

    @Override
    public boolean exist(Long id) {
        final String sql = "SELECT count(*) FROM articles WHERE id = :id AND deleted = false LIMIT 1";
        final Integer count = jdbcTemplate.queryForObject(sql,
                Map.of("id", id),
                Integer.class);
        return count > 0;
    }

    @Override
    public Article findWithSurroundingArticles(Long id) {
        final String sql = "SELECT id, title, writer, contents, createdAt, deleted, previousId, nextId "
                + " FROM (SELECT id, title, writer, contents, createdAt, deleted, "
                + " LAG(id, 1, 0) OVER(ORDER BY id ASC) AS previousId, "
                + " LEAD(id, 1, 0) OVER(ORDER BY id ASC) AS nextId "
                + " FROM articles WHERE deleted = false) WHERE id = :id AND deleted = false";

        return jdbcTemplate.queryForStream(sql, Map.of("id", id), articleRowMapper)
                .findFirst()
                .orElseThrow(ArticleNotFoundException::new);
    }

    @Override
    public int edit(Article article) {
        final String sql = "UPDATE articles SET title = :title, contents = :contents WHERE id = :id";
        Map<String, Object> parameter = Map.of(
                "id", article.getId(),
                "title", article.getTitle(),
                "contents", article.getContents());
        return jdbcTemplate.update(sql, parameter);
    }

    @Override
    public int delete(final Long id) {
        final String sql = "UPDATE articles SET deleted = true WHERE id = :id";
        Map<String, Object> parameter = Map.of(
                "id", id);
        return jdbcTemplate.update(sql, parameter);
    }
}
