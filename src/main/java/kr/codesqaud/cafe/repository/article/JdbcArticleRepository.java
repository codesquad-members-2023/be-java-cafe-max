package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class JdbcArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("article")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public void saveArticle(final Article article) {
        Map<String, Object> parameters = new ConcurrentHashMap<>();
//        parameters.put("id", article.getId());
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContents());
        parameters.put("writetime", article.getWriteTime());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());
    }

    @Override
    public List<Article> findAllArticle() {
        String sql = "select * from article";
        return jdbcTemplate.query(sql, articleRowMapper);
    }

    @Override
    public Optional<Article>  findArticleById(final int articleId) {
        String sql = "select * from article where id = ?";
        return jdbcTemplate.query(sql, articleRowMapper, articleId).stream().findAny();
    }

    private RowMapper<Article> articleRowMapper =  (rs, rowNum) ->
            new Article(rs.getLong("id"),rs.getString("writer"),
                    rs.getString("title"),rs.getString("content"),
                    rs.getTimestamp("writetime").toLocalDateTime());


}
