package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.controller.PostCreateRequest;
import kr.codesqaud.cafe.post.domain.Post;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;


    public JdbcPostRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post) {
        jdbcTemplate.update("INSERT INTO POSTS(title,writer,contents,wroteTime) VALUES (?, ?, ?, ?)",
                post.getTitle(),
                post.getWriter(),
                post.getContents(),
                post.getWroteTime()
        );

        return post;
    }

    @Override
    public Optional<Post> findByIndex(long index) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM POSTS WHERE INDEX = ?", postRowMapper, index));
        } catch (EmptyResultDataAccessException e) {
            System.out.println("실패");
            return Optional.empty();
        }
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query("SELECT * FROM POSTS", postRowMapper);
    }

    @Override
    public Post edit(PostCreateRequest postCreateRequest, long index) {
        jdbcTemplate.update("UPDATE POSTS SET title=?, contents=? WHERE index = ?",
                postCreateRequest.getTitle(),
                postCreateRequest.getContents(),
                index
        );
        return findByIndex(index).get();
    }

    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> new Post.Builder()
            .writer(rs.getString("writer"))
            .title(rs.getString("title"))
            .contents(rs.getString("contents"))
            .wroteTime(rs.getTimestamp("wroteTime"))
            .build()
            .setIndex(rs.getLong("index"));
}
