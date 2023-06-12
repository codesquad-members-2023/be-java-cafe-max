package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Repository
public class JdbcArticleRepository implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("articleTable").usingGeneratedKeyColumns("id");
    }

    @Override
    public void save(Article article) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("articleTable").usingGeneratedKeyColumns("id");
        Map<String, Object> param = new ConcurrentHashMap<>();
        param.put("writer", article.getWriter());
        param.put("title", article.getTitle());
        param.put("contents", article.getContents());
        param.put("createdTime", LocalDate.now());
        simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(param));
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        List<Article> result = jdbcTemplate.query("select * from articleTable where id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

        @Override
    public List<Article> getArticleList() {
        return jdbcTemplate.query("select * from articleTable", articleRowMapper());
    }

        @Override
    public void clearStore() {
        jdbcTemplate.update("delete from articleTable");
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime());
            article.setId(rs.getLong("id"));
            return article;
        };
    }
}
