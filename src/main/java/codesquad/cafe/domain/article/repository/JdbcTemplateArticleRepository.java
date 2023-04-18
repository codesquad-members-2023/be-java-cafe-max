package codesquad.cafe.domain.article.repository;

import codesquad.cafe.domain.article.domain.Article;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTemplateArticleRepository(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(final Article article, final String id) {
        String sql = "insert into article(title, contents, createdAt, writer_id) " +
                "values (:title, :contents, :createdAt, :writer_id) ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", article.getTitle());
        params.addValue("contents", article.getContents());
        params.addValue("createdAt", article.getCreatedAt());
        params.addValue("writer_id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<Article> findAll() {
        String sql = "select * from article";
        return namedParameterJdbcTemplate.query(sql,
                (rs, rowNum) -> new Article(
                            rs.getLong("id")
                            , rs.getString("title")
                            , rs.getString("contents")
                            , rs.getTimestamp("createdAt").toLocalDateTime()));
    }

    @Override
    public Article findById(final Long id){
        String sql = "SELECT * FROM article WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params,
                    (rs, rowNum) -> {
                        Article article = new Article(
                                rs.getLong("id")
                                , rs.getString("title")
                                , rs.getString("contents")
                                , rs.getTimestamp("createdAt").toLocalDateTime());
                    return article;
        });
    }
}
