package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.entity.Article;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleH2Repository implements ArticleRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ArticleH2Repository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        jdbcTemplate.update(
                "INSERT INTO article(writer, title, contents) VALUES (:writer, :title, :contents)",
                new BeanPropertySqlParameterSource(article)
        );
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("SELECT * FROM article", articleRowMapper());
    }

    @Override
    public Optional<Article> findByArticleId(long articleId) {
        List<Article> result = jdbcTemplate.query("SELECT * FROM article WHERE articleId = :articleId", articleRowMapper());
        return result.stream().findAny();
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(
                rs.getLong("articleId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents")
        );
    }
}
