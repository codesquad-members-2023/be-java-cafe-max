package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository // 자동으로 빈으로 등록
public class JdbcArticleRepository implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("articleTable").usingGeneratedKeyColumns("id");
    }

    @Override
    public void save(Article article) {  // SimpleJdbcInsert를 사용하고 있는데
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("articleTable").usingGeneratedKeyColumns("id");
        Map<String, Object> param = new ConcurrentHashMap<>();
        param.put("writer", article.getWriter());
        param.put("title", article.getTitle());
        param.put("contents", article.getContents());
        param.put("createdTime", LocalDate.now());
        simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(param));  // 이거 없으면 list(index)에 안 보이는데 영문을 모르겠(;;)
        // 맵을 이용한 SqlParamaeterSource...????? --> 그냥 Map을 넣어도 작동하는데 이게 무슨....
        // BeanPropertySqlParameterSource("tablename")라는 것도 있다는데....
    }

    @Override
    public Optional<Article> getArticleById(Long id) { // Long!
        List<Article> result = jdbcTemplate.query("select * from articleTable where articleId = ?", articleRowMapper(), id);
        // jdbcTemplate.query(String query, RowMapper<Article>, Long id) -> List<Article> 반환(???)
        return result.stream().findAny();
    }

        @Override
    public List<Article> getArticleList() { // 이거까지 작동된 듯(3-2)
        return jdbcTemplate.query("select * from articleTable", articleRowMapper());  // -> 뭔가 DB에서 정보들 가지고 Article 객체를 만들어 준다는 느낌..!?
        //  jdbcTemplate.query(String query, RowMapper<Article>) --> List<Article> 반환
    }

        @Override
    public void clearStore() {
        jdbcTemplate.update("delete from articleTable");
    } // 필요는 없는데 그냥 두기로..

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime());
            article.setId(rs.getLong("id"));
            return article;
        };
    }
}
