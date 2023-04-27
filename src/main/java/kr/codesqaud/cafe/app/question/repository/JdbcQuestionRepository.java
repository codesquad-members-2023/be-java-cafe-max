package kr.codesqaud.cafe.app.question.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcQuestionRepository implements QuestionRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcQuestionRepository.class);
    private final JdbcTemplate template;

    public JdbcQuestionRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Question> findAll() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(
                "SELECT q.ID, q.TITLE, q.CONTENT, q.CREATETIME, u.ID uid, u.NAME ")
            .append("FROM question q INNER JOIN users u ON q.USERID = u.ID ")
            .append("WHERE q.DELETED = false");
        return template.query(
            sqlBuilder.toString(),
            questionRowMapper());
    }

    @Override
    public Optional<Question> findById(Long id) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(
                "SELECT q.ID, q.TITLE, q.CONTENT, q.CREATETIME, u.ID uid, u.NAME ")
            .append("FROM question q INNER JOIN users u ON q.USERID = u.ID ")
            .append("WHERE q.ID = ? and q.DELETED = false");
        List<Question> result = template.query(
            sqlBuilder.toString(),
            questionRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Question save(Question question) {
        String sql = "INSERT INTO question(title, content, userId) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> getPreparedStatement(question, con, sql), keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);
        return findById(id).orElseThrow();
    }

    @Override
    public Question modify(Question question) {
        template.update("UPDATE question q SET q.TITLE = ?, q.CONTENT = ? WHERE q.ID = ?",
            question.getTitle(), question.getContent(), question.getId());
        return question;
    }

    private PreparedStatement getPreparedStatement(Question question, Connection con, String sql)
        throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
        pstmt.setString(1, question.getTitle());
        pstmt.setString(2, question.getContent());
        pstmt.setLong(3, question.getWriter().getId());
        return pstmt;
    }

    private RowMapper<Question> questionRowMapper() {
        return (rs, rowNum) ->
            Question.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .createTime(rs.getTimestamp("createTime").toLocalDateTime())
                .writer(User.builder()
                    .id(rs.getLong("uid"))
                    .name(rs.getString("name"))
                    .build())
                .build();
    }

    @Override
    public Question deleteById(Long id) {
        Question delQuestion = findById(id).orElseThrow();
        template.update("UPDATE question SET deleted = true WHERE ID = ?", id);
        return delQuestion;
    }
}
