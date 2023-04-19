package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.article.PrimaryKeyGenerationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class MySqlArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Article> articleRowMapper = BeanPropertyRowMapper.newInstance(Article.class);

    public MySqlArticleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Article article) {
        final String sql = "INSERT INTO Article (title, writer, contents, createdAt) VALUES (:title, :writer, :contents, :createdAt)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article), keyHolder);
        if (keyHolder.getKey() == null) {
            throw new PrimaryKeyGenerationException();
        }

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Article> findById(Long id) {
        final String sql = "SELECT id, title, writer, contents, createdAt FROM Article WHERE id = :id AND deleted = false LIMIT 1";
        try (final Stream<Article> result = jdbcTemplate.queryForStream(sql, Map.of("id", id), articleRowMapper)) {
            return result.findFirst();
        }
    }

    @Override
    public List<Article> findAll() {
        final String sql = "SELECT id, title, writer, contents, createdAt FROM Article WHERE deleted = false";
        return jdbcTemplate.query(sql, articleRowMapper);
    }

    @Override
    public boolean exist(Long id) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM Article WHERE id = :id AND deleted = false LIMIT 1)";
        return jdbcTemplate.queryForObject(sql,
                Map.of("id", id), Boolean.class);
    }

    @Override
    public Optional<Article> findWithSurroundingArticles(Long id) {
        final String sql = "SELECT id, title, writer, contents, createdAt, deleted, previousId, nextId "
                + " FROM (SELECT id, title, writer, contents, createdAt, deleted, "
                + " LAG(id, 1, null) OVER(ORDER BY id ASC) AS previousId, "
                + " LEAD(id, 1, null) OVER(ORDER BY id ASC) AS nextId "
                + " FROM Article WHERE deleted = false) WHERE id = :id AND deleted = false";

        try (final Stream<Article> result = jdbcTemplate.queryForStream(sql, Map.of("id", id), articleRowMapper)) {
            return result.findFirst();
        }
    }

    @Override
    public int edit(Article article) {
        final String sql = "UPDATE Article SET title = :title, contents = :contents WHERE id = :id";
        Map<String, Object> parameter = Map.of(
                "id", article.getId(),
                "title", article.getTitle(),
                "contents", article.getContents());
        return jdbcTemplate.update(sql, parameter);
    }

    @Override
    public int delete(final Long id) {
        final String sql = "UPDATE Article SET deleted = true WHERE id = :id";
        Map<String, Object> parameter = Map.of(
                "id", id);
        return jdbcTemplate.update(sql, parameter);
    }
}
