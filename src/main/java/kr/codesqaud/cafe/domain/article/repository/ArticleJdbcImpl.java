package kr.codesqaud.cafe.domain.article.repository;

import kr.codesqaud.cafe.domain.article.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleJdbcImpl implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;
    public ArticleJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        jdbcTemplate.update("INSERT INTO ARTICLES(WRITER,TITLE,CONTENTS) VALUES (?, ?, ?)",
                article.getWriter(),article.getTitle(),article.getContents());
    }
    private RowMapper<Article> rowMapper () {
        return (rs, rowNum) ->
                new Article(rs.getInt("IDX"),rs.getString("WRITER"),
                        rs.getString("TITLE"),rs.getString("CONTENTS"),
                        rs.getString("DATE"));
    }
    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM ARTICLES",rowMapper());
    }
}
