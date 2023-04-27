package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.controller.dto.request.ArticleWithReplyCount;
import kr.codesqaud.cafe.domain.Article;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcArticleRepository implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Article> articleMapper = (rs, rowNum) -> new Article(
            rs.getLong("id"),
            rs.getString("writer"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getTimestamp("created_at").toLocalDateTime()
    );

    public JdbcArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Article save(Article article) {
        jdbcTemplate.update("insert into articles(writer, title, content, created_at) values(?, ?, ?, ?)",
                article.getWriter(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt()
        );
        return article;
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("SELECT * FROM articles", articleMapper);
    }

    @Override
    public List<ArticleWithReplyCount> findAllArticlesWithReplyCount() {
        return jdbcTemplate.query("SELECT a.id, a.writer, a.title, a.created_at, COUNT(r.reply_id) AS reply_count "
                        + "FROM articles AS a "
                        + "LEFT JOIN replies AS r ON a.id = r.article_id "
                        + "GROUP BY a.id, a.writer, a.title, a.created_at ",
                (rs, rowNum) -> new ArticleWithReplyCount(rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getLong("reply_count")));
    }

    @Override
    public Optional<Article> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM articles WHERE id = ?", articleMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM articles WHERE id = ?", id);
    }

    @Override
    public void update(Article article) {
        jdbcTemplate.update("UPDATE articles SET title = ?, content = ? WHERE id = ?",
                article.getTitle(), article.getContent(), article.getId());
    }
}
