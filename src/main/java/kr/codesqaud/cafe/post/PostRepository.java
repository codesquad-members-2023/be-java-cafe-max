package kr.codesqaud.cafe.post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.exception.ErrorCode;
import kr.codesqaud.cafe.post.exception.SavePostFailedException;

@Repository
public class PostRepository {

	private final JdbcTemplate jdbcTemplate;
	private final DataSource dataSource;

	public PostRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	public int save(Post post) {
		try {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("POST")
				.usingGeneratedKeyColumns("POST_ID");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("TITLE", post.getTitle());
			parameters.put("NICKNAME", post.getNickname());
			parameters.put("TEXT_CONTENT", post.getTextContent());
			parameters.put("CREATE_DATETIME", Timestamp.valueOf(LocalDateTime.now()));
			return (int)simpleJdbcInsert.executeAndReturnKey(parameters);
		} catch (DataAccessException d) {
			throw new SavePostFailedException(ErrorCode.SAVE_POST_FAILED_CODE);
		}
	}

	public List<Post> getAllPosts() {
		return jdbcTemplate.query("SELECT * FROM POST", (resultSet, rowNum) ->
			new Post.Builder()
				.id(resultSet.getLong("POST_ID"))
				.nickname(resultSet.getString("NICKNAME"))
				.title(resultSet.getString("TITLE"))
				.textContent(resultSet.getString("TEXT_CONTENT"))
				.createdDateTime(resultSet.getTimestamp("CREATE_DATETIME").toLocalDateTime())
				.build()
		);
	}

	public Optional<Post> findById(Long postId) {
		try {
			RowMapper<Post> postRowMapper = (resultSet, rowNum) -> new Post.Builder()
				.id(postId)
				.nickname(resultSet.getString("NICKNAME").trim())
				.title(resultSet.getString("TITLE").trim())
				.textContent(resultSet.getString("TEXT_CONTENT").trim())
				.createdDateTime(resultSet.getTimestamp("CREATE_DATETIME").toLocalDateTime())
				.build();

			return Optional.ofNullable(jdbcTemplate.queryForObject(
				"SELECT POST_ID , NICKNAME, TITLE, TEXT_CONTENT, CREATE_DATETIME FROM POST WHERE POST_ID = ?",
				postRowMapper,
				postId));
		} catch (DataAccessException e) {
			return Optional.empty();
		}

	}

	public Optional<Post> findByTitle(String title) {
		try {
			RowMapper<Post> postRowMapper = (resultSet, rowNum) -> new Post.Builder()
				.id(resultSet.getLong("POST_ID"))
				.nickname(resultSet.getString("NICKNAME").trim())
				.title(resultSet.getString("TITLE").trim())
				.textContent(resultSet.getString("TEXT_CONTENT").trim())
				.createdDateTime(resultSet.getTimestamp("CREATE_DATETIME").toLocalDateTime())
				.build();

			return Optional.ofNullable(jdbcTemplate.queryForObject(
				"SELECT POST_ID , NICKNAME, TITLE, TEXT_CONTENT, CREATE_DATETIME FROM POST WHERE TITLE = ?",
				postRowMapper,
				title));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

}
