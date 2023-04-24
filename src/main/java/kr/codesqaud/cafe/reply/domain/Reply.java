package kr.codesqaud.cafe.reply.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reply {

	private String userId;

	private Long replyIdx;

	private Long articleIdx;

	private String date;

	private String nickName;

	private String content;

	public Reply(String userId, Long articleIdx, String nickName, String content) {
		this.userId = userId;
		this.articleIdx = articleIdx;
		this.nickName = nickName;
		this.content = content;
	}

	public Reply(ResultSet rs) throws SQLException {
		this.content = rs.getString("content");
		this.date = rs.getString("date");
		this.nickName = rs.getString("nickName");
		this.articleIdx = rs.getLong("article_idx");
		this.replyIdx = rs.getLong("reply_idx");
	}

	public String getUserId() {
		return userId;
	}

	public Long getArticleIdx() {
		return articleIdx;
	}

	public String getDate() {
		return date;
	}

	public String getNickName() {
		return nickName;
	}

	public String getContent() {
		return content;
	}

	public Long getReplyIdx() {
		return replyIdx;
	}
}
