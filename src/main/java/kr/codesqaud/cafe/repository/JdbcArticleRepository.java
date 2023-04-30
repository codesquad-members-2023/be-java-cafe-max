package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Primary
@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "insert into articles(writer, title, contents, writtenTime) values(?, ?, ?, current_timestamp)";
        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContents());
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from articles", articleRowMapper());
    }

    @Override
    public Article findById(long id) {
        List<Article> result = jdbcTemplate.query("select * from articles where id = ?", articleRowMapper(), id);
        return result.stream().findAny().get();
    }

    @Override
    public void update(Article article, long id) {
        String sql = "update articles set title = ?, contents = ? where id = ?";
        jdbcTemplate.update(sql, article.getTitle(), article.getContents(), id);
    }

    @Override
    public void delete(long id) {
        String sql = "delete from articles where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            return Article.builder()
                    .id(rs.getLong("id"))
                    .writer(rs.getString("writer"))
                    .title(rs.getString("title"))
                    .contents(rs.getString("contents"))
                    .writtenTime(rs.getTimestamp("writtenTime").toLocalDateTime())
                    .build();
        };
    }
}
