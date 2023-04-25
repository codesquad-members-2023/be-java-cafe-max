package codesquad.cafe.article.repository;

import codesquad.cafe.article.domain.Article;
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
                            , rs.getTimestamp("createdAt").toLocalDateTime()
                            ,rs.getString("writer_id")));
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
                                , rs.getTimestamp("createdAt").toLocalDateTime()
                                ,rs.getString("writer_id"));
                    return article;
        });
    }

    @Override
    public String findWriterByUserId(final String writerId) {
        String sql = "SELECT users.name FROM users JOIN article ON users.id = article.writer_id WHERE writer_id = :writerId";
        SqlParameterSource params = new MapSqlParameterSource("writerId", writerId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public void update(final Article article) {
        String sql = "update article set title = :title, contents = :contents where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", article.getId());
        params.addValue("title", article.getTitle());
        params.addValue("contents", article.getContents());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deletePostById(final Long postId) {
        String sql = "DELETE FROM article WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", postId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
