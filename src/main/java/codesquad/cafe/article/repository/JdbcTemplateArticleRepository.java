package codesquad.cafe.article.repository;

import codesquad.cafe.article.domain.Article;
import codesquad.cafe.global.util.Criteria;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTemplateArticleRepository(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(final Article article) {
        String sql = "insert into article(title, contents, createdAt, writer_id) " +
                "values (:title, :contents, :createdAt, :writer_id) ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", article.getTitle());
        params.addValue("contents", article.getContents());
        params.addValue("createdAt", article.getCreatedAt());
        params.addValue("writer_id", article.getWriterId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<Article> findPagingArticles(final Criteria criteria) {
        String sql = "SELECT id, title, contents, createdAt, writer_id FROM article WHERE status = true ORDER BY createdAt DESC LIMIT :offset, :limit";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("offset", criteria.getSkip());
        params.addValue("limit", criteria.getAmount());

        return namedParameterJdbcTemplate.query(sql, params,
                (rs, rowNum) -> new Article(
                        rs.getLong("id")
                        , rs.getString("title")
                        , rs.getString("contents")
                        , rs.getTimestamp("createdAt").toLocalDateTime()
                        ,rs.getString("writer_id")));
    }

    @Override
    public int getTotal() {
        String sql = "select count(id) from article";
        return namedParameterJdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
    }

    @Override
    public Article findById(final Long id){
        String sql = "SELECT id, title, contents, createdAt, writer_id FROM article WHERE id = :id AND status = true";
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
        String sql = "UPDATE article SET status = false WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", postId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
