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
public class ArticleJDBCRepository implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;

    //@Autowired //생성자가 한개면 생략 가능
    public ArticleJDBCRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("articleId");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setArticleId(key.longValue());

        return article;
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from article", articleRowMapper());
//        return null;
    }

    @Override
    public Optional<Article> findByArticleId(long articleId) {
        List<Article> result = jdbcTemplate.query("select * from article where articleId = ?", articleRowMapper(), articleId);
        return result.stream().findAny();
    }

    @Override
    public void clearArticleList() {
        jdbcTemplate.update("delete from article");
    }

    private RowMapper<Article> articleRowMapper(){
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setArticleId(rs.getLong("articleId"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            return article;
        };
    }
}
