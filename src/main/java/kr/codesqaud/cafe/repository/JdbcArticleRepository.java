package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "insert into article(writer, title, contents) values(?, ?, ?)";
        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContents());
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    @Override
    public Article findByIndex(int index) {
        List<Article> result = jdbcTemplate.query("select * from article where id = ?", articleRowMapper(), index + 1);
        return result.stream().findAny().get();
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            return Article.builder()
                    .writer(rs.getString("writer"))
                    .title(rs.getString("title"))
                    .contents(rs.getString("contents"))
                    .build();
        };
    }
}
