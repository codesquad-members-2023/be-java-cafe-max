package kr.codesqaud.cafe.article;

import java.util.List;
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
    public Article save(Article article) { // TODO: 저장할 때 ID가 아닌 name으로 바로 저장하게끔 수정(그러면 다른 메서드에서 join 안해도 될 듯)
        String sql = "insert into article (writer, title, contents) values (:writer, :title, :contents)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        return article;
    }

    @Override
    public String findIdBySequence(long sequence) {
        String sql = "select sequence, writer, title, contents from article where sequence = :sequence";
        SqlParameterSource param = new MapSqlParameterSource("sequence", sequence);
        return template.queryForObject(sql, param, articleRowMapper()).getWriter();
    }

    @Override
    public Optional<Article> findBySequence(long sequence) {
        String sql = "select a.sequence, u.name as writer, a.title, a.contents "
                + "from article a inner join users u on a.writer = u.userId where a.sequence = :sequence";
        SqlParameterSource param = new MapSqlParameterSource("sequence", sequence);
        try {
            return Optional.ofNullable(template.queryForObject(sql, param, articleRowMapper())); // TODO: RowMapper 대신 Article.class를 사용하면 에러 발생
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select a.sequence, u.name as writer, a.title, a.contents "
                + "from article a inner join users u on a.writer = u.userId";
        return template.query(sql, articleRowMapper());
    }

    @Override
    public Article update(long sequence, Article article) {
        String sql = "UPDATE article SET title = :title, contents = :contents where sequence = :sequence";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("sequence", sequence)
                .addValue("title", article.getTitle())
                .addValue("contents", article.getContents());
        template.update(sql, param);
        return article;
    }

    private RowMapper<Article> articleRowMapper() {
        return (resultSet, rowNumber) -> new Article(
                resultSet.getLong("sequence"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents")
        );
    }
}
