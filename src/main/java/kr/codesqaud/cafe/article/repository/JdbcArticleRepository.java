package kr.codesqaud.cafe.article.repository;

import kr.codesqaud.cafe.article.domain.Article;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcArticleRepository implements ArticleRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    //DB에서 id가 생성되기 때문에 저장하는 동시에 생성된 id를 반환한다.(테스트 코드에 쓰임)
    @Override
    public Long save(Article article) {
        String sql = "INSERT INTO articles (author, title, contents, time) VALUES (:author, :title, :contents, :time)";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(article);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }


    @Override
    public List<Article> findAll() {
        String sql = "SELECT * FROM articles";
        return namedParameterJdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public Article findById(long id) {
        String sql = "SELECT * FROM articles WHERE id = :id";

        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
            return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, articleRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Long modify(Article article) {
        String sql = "UPDATE articles SET title=:title, contents=:contents, time=:time WHERE id=:id";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(article);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        return article.getId();
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM articles WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(
                rs.getString("author"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getLong("id"),
                rs.getTimestamp("time").toLocalDateTime()
        );
    }
}
