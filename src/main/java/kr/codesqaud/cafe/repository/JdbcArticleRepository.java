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

@Repository
public class JdbcArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate();
    }
    //    @Override // 아 생각해보니 인터페이스 상속으로 만들지 않아서 오버라이드는 해당사항이 아닌..
    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); // insert 하게 해주는
        jdbcInsert.withTableName("articles_squad").usingGeneratedKeyColumns("id");
        // 방금 만든(h2) articles_squad 테이블에 insert를 하겠다 + (pk 알려주기)인 것으로 추정...

        Map<String, Object> parameters = new ConcurrentHashMap<>();  // 이것은 무엇?

        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("createdTime", article.getCreatedTime());
        parameters.put("articleNum", article.getArticleNum());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // Number 타입이 어디서 나온 것인지 몰라 당황(;;)
        article.setArticleId(key.longValue()); //  여기서 Id속성을 새로 만들어야 했는데 기존에 있던 articleNum으로 할 수 있지 않았을지 사소하고 소심한 궁금증이...
        return article;
    }

    //        @Override
    public Optional<Article> findById(Long id) {
        // import java.awt.List 하면 <T>밑에 빨간줄 있었던 <<<
        List<Article> result = jdbcTemplate.query("select * from articles_squad where id = ?",
                articleRowMapper(), id);
        return result.stream().findAny();
    }

    //        @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from articles_squad", // 'articles_squad' 테이블 선택 = sql문
                articleRowMapper() // 정체불명 -> 이제 만드는 메소드(RowMapper<T>)
        );
    }

    //        @Override
    public void clearStore() {
        jdbcTemplate.update( // 문자열로 쿼리문을 전송한다는 느낌(아마도 cs15에서 봤을 듯한...)
                "delete from articles_squad"
        );
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();  // 없던 기본 생성자 지금 만들기
            article.setArticleId(rs.getLong("id"));
            article.setWriter(rs.getString("writer"));    // 지금 가서 setter 만들기
            article.setTitle(rs.getString("title"));        // 지금 가서 setter 만들기
            article.setContents(rs.getString("contents"));      // 지금 가서 setter 만들기
            article.setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime());
            article.setArticleNum(rs.getLong("ArticleNum"));
            return article;
        };
    }
}
