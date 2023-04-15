package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
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
        String sql = "select * from article where writer = ?";
        List<Article> articleList = jdbcTemplate.query(sql, articleRowMapper(), writer);
        return articleList.stream().findAny();
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "select * from article where id = ?";
        List<Article> articleList = jdbcTemplate.query(sql, articleRowMapper(), id);
        return articleList.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    @Override
    public void updateWriter(String name, String updateName) {
        jdbcTemplate.update("update article set writer = ? where writer = ?", updateName, name);
    }

    @Override
    public boolean isCreatedBy(String userId, Long id) {
        Article article = jdbcTemplate.query("select * from article where id = ?", articleRowMapper(), id)
                .stream().findAny().get();
        if (article.getUserId().equals(userId)) {
            return true;
        }
        return false;
    }

    @Override
    public void updateTitle(Long id, String updateTitle) {
        jdbcTemplate.update("update article set title = ? where id = ?", updateTitle, id);
    }

    @Override
    public void updateContents(Long id, String updateContents) {
        jdbcTemplate.update("update article set contents = ? where id = ?", updateContents, id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from article where id = ?", id);
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
}
