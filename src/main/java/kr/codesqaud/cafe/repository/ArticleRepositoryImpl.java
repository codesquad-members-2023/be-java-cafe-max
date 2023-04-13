package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Article;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * H2 DB에 JdbcTemplate을 사용하여 게시글 데이터를 저장하는 저장소
 */
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final NamedParameterJdbcTemplate template;

    public ArticleRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Article save(Article article) {
        String sql = "insert into article (writer, title, contents) values (:writer, :title, :contents)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        return article;
    }

    @Override
    public Optional<Article> findBySequence(long sequence) {
        String sql = "select sequence, writer, title, contents from article where sequence = :sequence";
        SqlParameterSource param = new MapSqlParameterSource("sequence", sequence);
        try {
            return Optional.ofNullable(template.queryForObject(sql, param, articleRowMapper())); // TODO: RowMapper 대신 Article.class를 사용하면 에러 발생
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select sequence, writer, title, contents from article";
        return template.query(sql, articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (resultSet, rowNumber) -> new Article(
                resultSet.getLong("sequence"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents")
        );
    }
}
