package kr.codesqaud.cafe.app.comment.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.app.comment.entity.Comment;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.user.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class JdbcCommentRepository implements CommentRepository {

    private final JdbcTemplate template;

    public JdbcCommentRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Comment> findAll(Long questionId) {
        StringBuilder sql = new StringBuilder();
        sql
            .append("SELECT c.ID id, c.CONTENT content, c.CREATETIME createTime, ")
            .append("c.USERID uid, u.NAME name, c.QUESTIONID qid ")
            .append("FROM comment c INNER JOIN question q ON c.QUESTIONID = q.ID ")
            .append("INNER JOIN users u ON c.USERID = u.ID ")
            .append("WHERE q.ID = ? and c.DELETED = false");
        return template.query(sql.toString(), commentRowMapper(), questionId);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.ID id, c.CONTENT content, c.CREATETIME createTime, ")
            .append("c.USERID uid, u.NAME name, c.QUESTIONID qid ")
            .append("FROM comment c INNER JOIN question q ON c.QUESTIONID = q.ID ")
            .append("INNER JOIN users u ON c.USERID = u.ID ")
            .append("WHERE c.ID = ? and c.DELETED = false");
        List<Comment> result = template.query(sql.toString(), commentRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Comment save(Comment comment) {
        String sql = "INSERT INTO comment(content, questionId, userId) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> getPreparedStatement(comment, con, sql), keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);
        return findById(id).orElseThrow();
    }

    @Override
    public Comment modify(Comment comment) {
        template.update("UPDATE comment SET content = ? WHERE id = ?", comment.getContent(),
            comment.getId());
        return comment;
    }

    @Override
    public Comment deleteById(Long id) {
        Comment delComment = findById(id).orElseThrow();
        template.update("DELETE FROM comment WHERE id = ?", id);
        return delComment;
    }

    @Override
    public void deleteAllByQuestionId(Long questionId) {
        template.update("UPDATE comment SET deleted = true WHERE questionId = ?", questionId);
    }

    private PreparedStatement getPreparedStatement(Comment comment, Connection con, String sql)
        throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
        pstmt.setString(1, comment.getContent());
        pstmt.setLong(2, comment.getQuestion().getId());
        pstmt.setLong(3, comment.getWriter().getId());
        return pstmt;
    }

    private RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) ->
            Comment.builder()
                .id(rs.getLong("id"))
                .content(rs.getString("content"))
                .createTime(rs.getTimestamp("createTime").toLocalDateTime())
                .writer(User.builder()
                    .id(rs.getLong("uid"))
                    .name(rs.getString("name"))
                    .build())
                .question(Question.builder()
                    .id(rs.getLong("qid"))
                    .build())
                .build();
    }
}
