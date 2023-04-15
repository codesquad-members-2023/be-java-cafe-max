package kr.codesqaud.cafe.app.question.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.user.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcQuestionRepository implements QuestionRepository {

    private final JdbcTemplate template;
    private final UserService userService;

    public JdbcQuestionRepository(JdbcTemplate template, UserService userService) {
        this.template = template;
        this.userService = userService;
    }

    @Override
    public List<Question> findAll() {
        return template.query("SELECT * FROM question", questionRowMapper());
    }

    @Override
    public Optional<Question> findById(Long id) {
        List<Question> result =
            template.query("SELECT * FROM question WHERE id = ?", questionRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Question save(Question question) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> getPreparedStatementForSave(question, con), keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);
        return findById(id).orElseThrow();
    }

    private PreparedStatement getPreparedStatementForSave(Question question, Connection con)
        throws SQLException {
        String sql = "INSERT INTO question(title, content, writeDate, userId) VALUES(?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
        pstmt.setString(1, question.getTitle());
        pstmt.setString(2, question.getContent());
        pstmt.setString(3, question.getWriteDate().toString());
        pstmt.setLong(4, question.getUser().getId());
        return pstmt;
    }

    @Override
    public int deleteAll() {
        return template.update("DELETE FROM question");
    }

    private RowMapper<Question> questionRowMapper() {
        return (rs, rowNum) -> new Question(rs.getLong("id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getTimestamp("writeDate").toLocalDateTime(),
            userService.findUser(rs.getLong("userId")));
    }
}
