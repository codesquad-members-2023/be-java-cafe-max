package kr.codesqaud.cafe.repository.impl;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JDBCArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        jdbcTemplate.update("INSERT INTO ARTICLE (title, content, date) VALUES (?, ?, ?)",
                article.getTitle(),article.getContent(),article.getDate());
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("SELECT * FROM ARTICLE", (rs,rn) -> new Article(rs));
    }

    @Override
    public Optional<Article> findArticleById(int id) {
        Article article = jdbcTemplate.queryForObject("SELECT * FROM ARTICLE WHERE id = ?",new Object[]{id},(rs,rn) -> new Article(rs));
        return Optional.ofNullable(article);
    }
}
