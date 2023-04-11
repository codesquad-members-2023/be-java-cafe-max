package kr.codesqaud.cafe.domain.article.repository;

import kr.codesqaud.cafe.domain.article.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleJdbcRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;
    public ArticleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        jdbcTemplate.update("INSERT INTO ARTICLES(ID,WRITER,TITLE,CONTENTS) VALUES (?,?, ?, ?)",
                article.getUserId(),article.getWriter(),article.getTitle(),article.getContents());
    }
    private RowMapper<Article> rowMapper () {
        return (rs, rowNum) ->
                new Article.Builder()
                        .index(rs.getInt("IDX"))
                        .userId(rs.getString("ID"))
                        .writer(rs.getString("WRITER"))
                        .title(rs.getString("TITLE"))
                        .contents(rs.getString("CONTENTS"))
                        .date( rs.getString("DATE"))
                        .build();
    }
    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query(
                "SELECT IDX , ID , WRITER , TITLE , CONTENTS , DATE  FROM ARTICLES ORDER BY IDX DESC ",rowMapper());
    }

    @Override
    public Article findByIdx(int idx) {
        return jdbcTemplate.queryForObject(
                "SELECT IDX , ID , WRITER , TITLE , CONTENTS , DATE FROM ARTICLES WHERE IDX = ?", rowMapper(), idx
        );
    }

    @Override
    public void update(Article article) {
        jdbcTemplate.update(
                "UPDATE ARTICLES SET TITLE = ? ,CONTENTS = ? WHERE IDX = ?"
                ,article.getTitle(),article.getContents(),article.getIndex()
        );
    }
}