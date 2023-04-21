package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.controller.article.ArticleForm;
import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JdbcArticleRepository implements ArticleRepository{

    private final JdbcTemplate jdbcTemplate;
    public JdbcArticleRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("articles").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("createdAt", article.getCreatedAt());
        parameters.put("points", article.getPoints());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        List<Article> result = jdbcTemplate.query("select * from articles where id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from articles", articleRowMapper());
    }

    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from articles");
    }

    private RowMapper<Article> articleRowMapper(){
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            if (rs.getTimestamp("modifiedAt") != null) {
                article.setModifiedAt(rs.getTimestamp("modifiedAt").toLocalDateTime());
            }
            article.setPoints(rs.getLong("points"));
            return article;
        };
    }

    @Override
    public Optional<Article> update(Long id, ArticleForm form) {
        jdbcTemplate.update("update articles set title = ? where id = ?", form.getTitle(), id);
        jdbcTemplate.update("update articles set contents = ? where id = ?", form.getContents(), id);
        jdbcTemplate.update("update articles set modifiedAt = ? where id = ?", LocalDateTime.now(), id);
        return findById(id);
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("delete from articles where id=?", id);
        return id;
    }
}
