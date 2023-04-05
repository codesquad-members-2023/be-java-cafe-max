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

	public static final String TABLE_NAME = "POST";
	public static final String COLUMN_ID = "POST_ID";
	public static final String COLUMN_TITLE = "TITLE";
	public static final String COLUMN_NICKNAME = "NICKNAME";
	public static final String COLUMN_TEXT_CONTENT = "TEXT_CONTENT";
	public static final String COLUMN_CREATE_DATETIME = "CREATE_DATETIME";
	public static final String QUERY_SELECT_ALL = "SELECT * FROM POST";
	public static final String QUERY_FIND_BY_ID = "SELECT POST_ID , NICKNAME, TITLE, TEXT_CONTENT, CREATE_DATETIME FROM POST WHERE POST_ID = ?";
	public static final String QUERY_FIND_BY_TITLE = "SELECT POST_ID , NICKNAME, TITLE, TEXT_CONTENT, CREATE_DATETIME FROM POST WHERE TITLE = ?";
	private final JdbcTemplate jdbcTemplate;
	private final DataSource dataSource;

	public PostRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	public int save(Post post) {
		try {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
				.usingGeneratedKeyColumns(COLUMN_ID);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(COLUMN_TITLE, post.getTitle());
			parameters.put(COLUMN_NICKNAME, post.getNickname());
			parameters.put(COLUMN_TEXT_CONTENT, post.getTextContent());
			parameters.put(COLUMN_CREATE_DATETIME, Timestamp.valueOf(LocalDateTime.now()));
			return (int)simpleJdbcInsert.executeAndReturnKey(parameters);
		} catch (DataAccessException d) {
			throw new SavePostFailedException(ErrorCode.SAVE_POST_FAILED_CODE);
		}
	}

	public List<Post> getAllPosts() {
		return jdbcTemplate.query(QUERY_SELECT_ALL, (resultSet, rowNum) ->
			new Post.Builder()
				.id(resultSet.getLong(COLUMN_ID))
				.nickname(resultSet.getString(COLUMN_NICKNAME))
				.title(resultSet.getString(COLUMN_TITLE))
				.textContent(resultSet.getString(COLUMN_TEXT_CONTENT))
				.createdDateTime(resultSet.getTimestamp(COLUMN_CREATE_DATETIME).toLocalDateTime())
				.build()
		);
	}

	public Optional<Post> findById(Long postId) {
		try {
			RowMapper<Post> postRowMapper = (resultSet, rowNum) -> new Post.Builder()
				.id(postId)
				.nickname(resultSet.getString(COLUMN_NICKNAME).trim())
				.title(resultSet.getString(COLUMN_TITLE).trim())
				.textContent(resultSet.getString(COLUMN_TEXT_CONTENT).trim())
				.createdDateTime(resultSet.getTimestamp(COLUMN_CREATE_DATETIME).toLocalDateTime())
				.build();

			return Optional.ofNullable(jdbcTemplate.queryForObject(
				QUERY_FIND_BY_ID,
				postRowMapper,
				postId));
		} catch (DataAccessException e) {
			return Optional.empty();
		}

	}

	public Optional<Post> findByTitle(String title) {
		try {
			RowMapper<Post> postRowMapper = (resultSet, rowNum) -> new Post.Builder()
				.id(resultSet.getLong(COLUMN_ID))
				.nickname(resultSet.getString(COLUMN_NICKNAME).trim())
				.title(resultSet.getString(COLUMN_TITLE).trim())
				.textContent(resultSet.getString(COLUMN_TEXT_CONTENT).trim())
				.createdDateTime(resultSet.getTimestamp(COLUMN_CREATE_DATETIME).toLocalDateTime())
				.build();

			return Optional.ofNullable(jdbcTemplate.queryForObject(
				QUERY_FIND_BY_TITLE,
				postRowMapper,
				title));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

}
