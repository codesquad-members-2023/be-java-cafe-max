package kr.codesqaud.cafe.repository.impl;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JDBCArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        jdbcTemplate.update("INSERT INTO Article (title, content) VALUES (?, ?)",
                article.getTitle(),article.getContent());
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("SELECT * FROM Article", (rs,rn) -> new Article(rs));
    }

    @Override
    public Article findArticleById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Article WHERE id = ?",new Object[]{id},(rs,rn) -> new Article(rs));
    }
}
