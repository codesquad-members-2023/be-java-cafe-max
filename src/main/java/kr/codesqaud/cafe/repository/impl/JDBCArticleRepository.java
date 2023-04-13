package kr.codesqaud.cafe.repository.impl;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("jdbcRepository")
public class JDBCArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        jdbcTemplate.update("INSERT INTO ARTICLE (title, content, date, id,nickName) VALUES (?, ?, ?, ?, ?)",
                article.getTitle(),article.getContent(),article.getDate(),article.getId(),article.getNickName());
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("SELECT * FROM ARTICLE", (rs,rn) -> new Article(rs));
    }

    @Override
    public Optional<Article> findArticleById(int idx) {
        Article article = jdbcTemplate.queryForObject("SELECT * FROM ARTICLE WHERE idx = ?",new Object[]{idx},(rs,rn) -> new Article(rs));
        return Optional.ofNullable(article);
    }

    @Override
    public void updateArticle(Article article) {
        jdbcTemplate.update("UPDATE ARTICLE SET title = ?, content = ? WHERE idx = ?", article.getTitle(), article.getContent(), article.getIdx());
    }
}
