package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Article> articleRowMapper = BeanPropertyRowMapper.newInstance(Article.class);

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("ARTICLES")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Article save(Article article) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        Number key = simpleJdbcInsert.executeAndReturnKey(param);
        article.setId(key.longValue());
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "select ID, USERID, TITLE, CONTENTS, CURRENTTIME from ARTICLES where ID = :id";

        try {
            Map<String, Object> param = Map.of("id", id);
            Article article = template.queryForObject(sql, param, articleRowMapper);
            return Optional.ofNullable(article);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select ID, USERID, TITLE, CONTENTS, CURRENTTIME from ARTICLES";
        return template.query(sql, articleRowMapper);
    }

    @Override
    public void update(Long id, Article article) {
        String sql = "update ARTICLES " +
                "set TITLE=:title, CONTENTS=:contents " +
                "where ID=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", article.getTitle())
                .addValue("contents", article.getContents())
                .addValue("id", id);

        template.update(sql, param);
    }

    @Override
    public void deleteArticle(Long id) {
        String sql = "delete from ARTICLES where id=:id";

        Map<String, Object> param = Map.of("id", id);
        template.update(sql, param);
    }
}
