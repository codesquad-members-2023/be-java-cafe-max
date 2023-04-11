package kr.codesqaud.cafe.article;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
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
    public List<Article> findAll() {
        return null;
    }

    @Override
    public Article findById(int articleId) {
        return null;
    }
}
