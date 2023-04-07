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

        // #1
//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("articles").usingGeneratedKeyColumns("id");
//
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("writer", article.getWriter());
//        parameters.put("title", article.getTitle());
//        parameters.put("contents", article.getContents());
//
//        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
//        article.setId(key.longValue());

        // #2
//        String sql = "insert into articles (writer, title, contents, ts) values (?, ?, ?, now())";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jc.update(con -> {
//            // 자동 증가 키
//            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
//            ps.setString(1, article.getWriter());
//            ps.setString(2, article.getTitle());
//            ps.setString(3, article.getContents());
//            return ps;
//        }, keyHolder);
//
//        Long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
//        article.setId(key);
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

//        List<Article> result = jdbcTemplate.query("select * from articles where id = ?", articleRowMapper(), id);
//        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        String sql = "select ID, WRITER, TITLE, CONTENTS, CURRENTTIME from ARTICLES";
        return template.query(sql, articleRowMapper());
//        return jdbcTemplate.query("select * from articles", articleRowMapper());
    }

    // TODO: 필드 or 메서드
    private RowMapper<Article> articleRowMapper() {
        return BeanPropertyRowMapper.newInstance(Article.class);
//        return (rs, rowNum) -> {
//            Article article = new Article();
//            article.setId(rs.getLong("id"));
//            article.setWriter(rs.getString("writer"));
//            article.setTitle(rs.getString("title"));
//            article.setContents(rs.getString("contents"));
//            return article;
//        };
    }
}
