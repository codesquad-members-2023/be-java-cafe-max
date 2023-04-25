package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.dto.DataType;
import kr.codesqaud.cafe.domain.vo.PageForm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {
    private final NamedParameterJdbcTemplate template;
    private final RowMapper<Article> articleRowMapper = BeanPropertyRowMapper.newInstance(Article.class);

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        String sql = "insert into " + DataType.ARTICLES.getType() + " (user_id, title, contents, currentTime, deleted) " +
                "values (:userId, :title, :contents, :currentTime, false)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        template.update(sql, param);
        return article;
    }

    @Override
    public synchronized Optional<Article> findById(Long id) {
        String sql = "select a.id, a.title, a.contents, a.currentTime, a.user_id, " +
                "count(r.article_id) as replyCount " +
                "from " + DataType.ARTICLES.getType() + " a " +
                "left join " + DataType.REPLIES.getType() + " r " + "on a.id=r.article_id and r.deleted=false " +
                "where a.id=:id and a.deleted=false " +
                "group by a.id, a.title, a.contents, a.currentTime, a.user_id";

        try {
            Map<String, Object> param = Map.of("id", id);
            Article article = template.queryForObject(sql, param, articleRowMapper);
            return Optional.ofNullable(article);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public synchronized List<Article> findAll(PageForm pageForm) {
        String sql = "select a.id, a.title, a.contents, a.currentTime, a.user_id, u.user_id, " +
                "count(r.article_id) as replyCount " +
                "from " + DataType.ARTICLES.getType() + " a " +
                "join " + DataType.USERS.getType() + " u on a.user_id = u.user_id " +
                "left join " + DataType.REPLIES.getType() + " r on a.id = r.article_id and r.deleted = false " +
                "where a.deleted = false " +
                "group by a.id, a.title, a.contents, a.currentTime, a.user_id, u.user_id " +
                "order by a.id desc " +
                "limit :start, :cntPerPage";

        Map<String, Integer> param = Map.of("start", pageForm.getStart(), "cntPerPage", pageForm.getCntPerPage());
        return template.query(sql, param, articleRowMapper);
    }

    @Override
    public void update(Long id, Article article) {
        String sql = "update " + DataType.ARTICLES.getType() +
                " set title=:title, contents=:contents " +
                "where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", article.getTitle())
                .addValue("contents", article.getContents())
                .addValue("id", id);

        template.update(sql, param);
    }

    @Override
    public void deleteArticle(Long id) {
        String articleDeletedSql = "update " + DataType.ARTICLES.getType() + " set deleted=true where id=:id";

        Map<String, Object> param = Map.of("id", id);
        template.update(articleDeletedSql, param);
    }

    @Override
    public Long count() {
        String sql = "select count(*) from " + DataType.ARTICLES.getType() + " where deleted=:flag";
        return template.queryForObject(sql, Map.of("flag", false), Long.class);
    }
}
