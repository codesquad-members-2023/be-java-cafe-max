package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class JdbcArticleRepositoryImpl implements ArticleRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcArticleRepositoryImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long save(final Article article) {
        final String sql = "INSERT INTO article (user_fk, title, contents, created_at, updated_at) " +
                "VALUES (:userFk, :title, :contents, :createdAt, :updatedAt)";

        final SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Article> findById(final Long id) {
        final String sql = "" +
                "SELECT article.id, article.user_fk, users.user_id as writer , article.title, article.contents, article.created_at, article.updated_at " +
                "FROM article " +
                "JOIN users ON article.user_fk = users.id " +
                "WHERE article.id = :id";

        try {
            return Optional.ofNullable(template.queryForObject(sql, Map.of("id", id), articleRowMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        final String sql = "" +
                "SELECT article.id, article.user_fk, users.user_id as writer , article.title, article.contents, article.created_at, article.updated_at " +
                "FROM article " +
                "JOIN users ON article.user_fk = users.id";

        return template.query(sql, articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(
                rs.getLong("id"),
                rs.getLong("user_fk"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
