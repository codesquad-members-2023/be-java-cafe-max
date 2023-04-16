package kr.codesqaud.cafe.domain.article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class ArticleJdbcRepository implements ArticleRepository {

    private final JdbcTemplate template;
    private final UserService userService;

    @Autowired
    public ArticleJdbcRepository(JdbcTemplate template, UserService userService) {
        this.template = template;
        this.userService = userService;
    }

    @Override
    public List<Article> findAll() {
        return template.query("SELECT * FROM article", articleRowMapper());
    }

    @Override
    public Optional<Article> findById(Long id) {
        List<Article> result =
            template.query("SELECT * FROM article WHERE id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Article save(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> getPreparedStatementForSave(article, con), keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);
        return findById(id).orElseThrow();
    }

    private PreparedStatement getPreparedStatementForSave(Article article, Connection con)
        throws SQLException {
        String sql = "INSERT INTO article(title, content, writeDate, userId) VALUES(?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
        pstmt.setString(1, article.getTitle());
        pstmt.setString(2, article.getContent());
        pstmt.setString(3, article.getWriteDate().toString());
        pstmt.setLong(4, article.getUser().getId());
        return pstmt;
    }

    @Override
    public int deleteAll() {
        return template.update("DELETE FROM article");
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(rs.getLong("id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getTimestamp("writeDate").toLocalDateTime(),
            userService.findUser(rs.getLong("userId")));
    }
}
