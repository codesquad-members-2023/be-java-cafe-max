package kr.codesqaud.cafe.domain.reply.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.domain.reply.entity.Reply;
import kr.codesqaud.cafe.domain.reply.repository.ReplyRepository;

@Repository
@Transactional(readOnly = true)
public class JdbcReplyRepositoryImpl implements ReplyRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final RowMapper<Reply> replyRowMapper = BeanPropertyRowMapper.newInstance(Reply.class);

	public JdbcReplyRepositoryImpl(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	@Transactional
	public void save(Reply reply) {
		final String SQL = "INSERT INTO reply ( articleId, writer, content, dateTime) VALUES (:articleId,:writer, :content, :dateTime)";
		namedParameterJdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(reply));
	}

	@Override
	public List<Reply> findByArticleId(Long articleId) {
		final String SQL = "SELECT * FROM reply WHERE articleId = :articleId";
		try {
			return namedParameterJdbcTemplate.queryForStream(SQL, Map.of("articleId", articleId), replyRowMapper)
				.collect(
					Collectors.toList());
		} catch (DataAccessException e) {
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	public void delete(String replyId) {
		final String SQL = "DELETE FROM reply WHERE id = :replyId";
		namedParameterJdbcTemplate.update(SQL, Map.of("replyId", replyId));

	}

	@Override
	public Optional<Reply> findById(String id) {
		final String SQL = "SELECT * FROM reply WHERE id = :id";
		try {
			return namedParameterJdbcTemplate.queryForStream(SQL, Map.of("id", id), replyRowMapper)
				.findFirst();
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}
}
