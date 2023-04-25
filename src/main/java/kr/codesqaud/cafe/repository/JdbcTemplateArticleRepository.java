package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.SimpleArticle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        String sql = "insert into article (userId, writer, title, contents) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                article.getUserId(),
                article.getWriter(),
                article.getTitle(),
                article.getContents());
        return article;
    }

    @Override
    public Optional<Article> findByWriter(String writer) {
        String sql = "select * from article where writer = ? and deleted = false";
        List<Article> articleList = jdbcTemplate.query(sql, articleRowMapper(), writer);
        return articleList.stream().findAny();
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "select * from article where id = ? and deleted = false";
        List<Article> articleList = jdbcTemplate.query(sql, articleRowMapper(), id);
        return articleList.stream().findAny();
    }

    @Override
    public List<SimpleArticle> findAll() {
        return jdbcTemplate.query("select writer, userId, title, id, createdTime from article where deleted = false", simpleArticleRowMapper());
    }

    @Override
    public void updateWriter(String name, String updateName) {
        jdbcTemplate.update("update article set writer = ? where writer = ?", updateName, name);
    }


    @Override
    public void updateArticle(Article article) {
        jdbcTemplate.update("update article set title = ?, contents = ? where id = ?", article.getTitle(), article.getContents(), article.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("update article set deleted = ? where id = ?", true, id);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(
                    rs.getString("userId"),
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getTimestamp("createdTime").toLocalDateTime());
            article.setId(rs.getLong("id"));
            return article;
        };
    }

    private RowMapper<SimpleArticle> simpleArticleRowMapper() {
        return (rs, rowNum) -> {
            SimpleArticle article = new SimpleArticle();
            article.setWriter(rs.getString("writer"));
            article.setUserId(rs.getString("userId"));
            article.setTitle(rs.getString("title"));
            article.setId(rs.getLong("id"));
            article.setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime());
            return article;
        };
    }
}

