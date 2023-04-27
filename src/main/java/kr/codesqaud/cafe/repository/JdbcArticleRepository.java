package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository // 자동으로 빈으로 등록
public class JdbcArticleRepository {
//        implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("articleTable").usingGeneratedKeyColumns("id");
    }
//        @Override
    public void save(Article article) {  // SimpleJdbcInsert를 사용하고 있는데
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("articleTable").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("createdTime", LocalDate.now());

        long id = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)).longValue(); // 맵을 이용한 SqlParamaeterSource...?????  // BeanPropertySqlParameterSource("tablename")라는 것도 있다는데....
        article.setId(id);
    }
//    @Override
    public Optional<Article> getArticleById(Long id) { // Long!
        List<Article> result = jdbcTemplate.query("select * from articleTable where articleId = ?", articleRowMapper(), id);
        // jdbcTemplate.query(String query, RowMapper<Article>, Long id) -> List<Article> 반환(???)
        return result.stream().findAny();
    }
//    @Override
    public List<Article> getArticleList() {
        return jdbcTemplate.query("select * from articleTable", articleRowMapper() );  // -> 뭔가 DB에서 정보들 가지고 Article 객체를 만들어 준다는 느낌..!?
        //  jdbcTemplate.query(String query, RowMapper<Article>) --> List<Article> 반환
    }
//    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from articleTable" );
    }
    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime());
            article.setId(rs.getLong("id"));
            return article;
        } ;
    }
}
