package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("articles")
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
        String sql = "select ID, WRITER, TITLE, CONTENTS, CURRENTTIME from ARTICLES where ID = :id";

        try {
            Map<String, Object> param = Map.of("id", id);
            Article article = template.queryForObject(sql, param, articleRowMapper());
            assert article != null;
            return Optional.of(article);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select ID, WRITER, TITLE, CONTENTS, CURRENTTIME from ARTICLES";
        return template.query(sql, articleRowMapper());
    }

    // TODO: 이대로 메서드로 두는 것이 좋은지, 필드로 빼는 것이 좋은지 판단 후 재구현
    private RowMapper<Article> articleRowMapper() {
        return BeanPropertyRowMapper.newInstance(Article.class);
    }
}
