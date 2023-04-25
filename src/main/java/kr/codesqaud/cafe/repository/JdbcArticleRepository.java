package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
@Repository // 자동으로 빈으로 등록
public class JdbcArticleRepository implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;
    public JdbcArticleRepository(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}
        @Override
    public void save(Article article) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("articles_squad").usingGeneratedKeyColumns("articleNum"); // 테이블 + pk(인덱스?)인 듯
        // == 'SimpleJdbcInsertOperations'
        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("createdTime", article.getCreatedTime());
//        parameters.put("articleNum", article.getArticleNum());
        long articleNum = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)).longValue(); // 맵을 이용한 SqlParamaeterSource...?????  // BeanPropertySqlParameterSource("tablename")라는 것도 있다는데....
        article.setArticleNum(articleNum);
    }
    @Override
    public Optional<Article> getArticleByArticleNum(Long articleNum) { // Long!
        // import java.awt.List 하면 <T>밑에 빨간줄 있었던 <<<
        List<Article> result = jdbcTemplate.query("select * from articles_squad where articleId = ?", articleRowMapper(), articleNum);
        // jdbcTemplate.query(String query, RowMapper<Article>, Long id) -> List<Article> 반환(???)
        return result.stream().findAny();
    }
    @Override
    public List<Article> getArticleList() {
        return jdbcTemplate.query("select * from articles_squad", articleRowMapper() );  // -> 뭔가 DB에서 정보들 가지고 Article 객체를 만들어 준다는 느낌..!?
        //  jdbcTemplate.query(String query, RowMapper<Article>) --> List<Article> 반환
        // -----> findAll이면 '다 주세요' 인데???
    }
    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from articles_squad" );
        //  jdbcTemplate.update(String 쿼리문)
    }
    private RowMapper<Article> articleRowMapper() { // RowMapper<T>인데 아무래도 'T를 만들어드립니다'라는 뜻인듯... -> RowMapper<Article>을 정의해서 반환
        return (rs, rowNum) -> {   // (resultSet, current row)    // query에 id(아 이게 rowNum인가?)가 있으면 1개 레코드만 주고 없으면 맨 위에서 맨 아래까지 다 긁어서 준다는?
            Article article = new Article();
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime());
            article.setArticleNum(rs.getLong("ArticleNum"));
            return article;
        } ;
    }
}
