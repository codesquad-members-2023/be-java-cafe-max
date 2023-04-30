package kr.codesqaud.cafe.article;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * H2 DB에 JdbcTemplate을 사용하여 게시글 데이터를 저장하는 저장소
 */
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final NamedParameterJdbcTemplate template;

    public ArticleRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public long save(Article article) { // TODO: 저장할 때 ID가 아닌 name으로 바로 저장하게끔 수정(그러면 다른 메서드에서 join 안해도 될 듯)
        String sql = "insert into article (user_login_Id, title, contents) values (:loginId, :title, :contents)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public String findLoginIdOf(long articleId) {
        String sql = "SELECT id, user_login_Id, title, contents, is_deleted, has_reply FROM article WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", articleId);
        return Objects.requireNonNull(template.queryForObject(sql, param, articleRowMapper())).getLoginId();
    }

    @Override
    public Optional<Article> findOneById(long id) { // TODO: 조인 쿼리 수정 필요, 수정 후 위에 메서드 삭제
        String sql = "SELECT a.id, u.name AS user_login_id, a.title, a.contents, a.is_deleted, a.has_reply "
                + "FROM article a INNER JOIN user u ON a.user_login_id = u.login_id WHERE a.id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(template.queryForObject(sql, param, articleRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT a.id, u.name AS user_login_id, a.title, a.contents, a.is_deleted, a.has_reply "
                + "FROM article a INNER JOIN user u ON a.user_login_id = u.login_id "
                + "WHERE a.is_deleted = false";
        return template.query(sql, articleRowMapper());
    }

    @Override
    public long update(long id, Article article) {
        String sql = "UPDATE article SET title = :title, contents = :contents where id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("title", article.getTitle())
                .addValue("contents", article.getContents());
        template.update(sql, param);
        return id;
    }

    @Override
    public void delete(long articleId) {
        String sql = "UPDATE article SET is_deleted = true WHERE id = :articleId";
        SqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
        template.update(sql, param);
    }

    private RowMapper<Article> articleRowMapper() {
        return (resultSet, rowNumber) -> new Article.Builder()
                .id(resultSet.getLong("id"))
                .loginId(resultSet.getString("user_login_id"))
                .title(resultSet.getString("title"))
                .contents(resultSet.getString("contents"))
                .isDeleted(resultSet.getBoolean("is_deleted"))
                .hasReply(resultSet.getBoolean("has_reply"))
                .build();
    }
}
