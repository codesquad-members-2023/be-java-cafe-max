package kr.codesqaud.cafe.repository;

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


//todo : JdbcTemplate vs NamedParameterJdbcTemplate 비교해보기
@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

//    private final JdbcTemplate jdbcTemplate;

//    public JdbcTemplateArticleRepository(DataSource dataSource) {
//        jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("ARTICLE_TB")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public void save(Article article) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        Number key = simpleJdbcInsert.executeAndReturnKey(param);
        article.setId(key.longValue());
    }

//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("ARTICLE_TB").usingGeneratedKeyColumns("ID");
//
//        Map<String, Object> articleParameters = new HashMap<>();
//        articleParameters.put("TITLE", article.getTitle());
//        articleParameters.put("CONTENT", article.getContent());
//        articleParameters.put("AUTHOR", article.getAuthor());
//
//        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(articleParameters));
//        article.setId(key.longValue());

    @Override
    public void update(Article updateArticle) {
        String sql = "UPDATE ARTICLE_TB SET TITLE = :TITLE, CONTENT = :CONTENT WHERE ID = :ID";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("TITLE", updateArticle.getTitle())
                .addValue("CONTENT", updateArticle.getContent())
                .addValue("ID", updateArticle.getId());

        template.update(sql, param);
    }

//        String sql = "UPDATE ARTICLE_TB SET TITLE = ?, CONTENT = ? WHERE ID = ?";
//        jdbcTemplate.update(sql, updateArticle.getTitle(), updateArticle.getContent(), updateArticle.getId());

    @Override
    public List<Article> gatherAll() {
        String sql = "SELECT * FROM ARTICLE_TB";
        return template.query(sql, articleRowMapper());
    }

//        return jdbcTemplate.query("SELECT * FROM ARTICLE_TB", articleRowMapper());

    @Override
    public Optional<Article> findById(long id) {
        String sql = "SELECT * FROM ARTICLE_TB WHERE ID = :ID";

        try {
            Map<String, Object> param = Map.of("ID", id);
            Article article = template.queryForObject(sql, param, articleRowMapper());
            assert article != null;
            return Optional.of(article);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    List<Article> wantedPost = jdbcTemplate.query("SELECT * FROM ARTICLE_TB WHERE ID = ?", articleRowMapper(), id);
//        return wantedPost.stream().findAny();

    public RowMapper<Article> articleRowMapper() {
        return BeanPropertyRowMapper.newInstance(Article.class);
        /*
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setPrice(rs.getInt("price"))
         */


//        return (rs, rowNum) -> {
//            Article article = new Article();
//            article.setId(rs.getLong("ID"));
//            article.setTitle(rs.getString("TITLE"));
//            article.setContent(rs.getString("CONTENT"));
//            article.setAuthor(rs.getString("AUTHOR"));
//            return article;
//        };
    }
}
