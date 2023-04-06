package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("ARTICLE_TB").usingGeneratedKeyColumns("ID");

        Map<String, Object> articleParameters = new HashMap<>();
        articleParameters.put("TITLE", article.getTitle());
        articleParameters.put("CONTENT", article.getContent());
        articleParameters.put("AUTHOR", article.getAuthor());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(articleParameters));
        article.setId(key.longValue());
    }

    @Override
    public void update(Article updateArticle) {
        String sql = "UPDATE ARTICLE_TB SET TITLE = ?, CONTENT = ? WHERE ID = ?";
        jdbcTemplate.update(sql, updateArticle.getTitle(), updateArticle.getContent(), updateArticle.getId());
    }

    @Override
    public List<Article> gatherAll() {
        return jdbcTemplate.query("SELECT * FROM ARTICLE_TB", articleRowMapper());
    }

    @Override
    public Optional<Article> findById(long id) {
        List<Article> wantedPost = jdbcTemplate.query("SELECT * FROM ARTICLE_TB WHERE ID = ?", articleRowMapper(), id);
        return wantedPost.stream().findAny();
    }

    public RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("ID"));
            article.setTitle(rs.getString("TITLE"));
            article.setContent(rs.getString("CONTENT"));
            article.setAuthor(rs.getString("AUTHOR"));
            return article;
        };
    }
}
